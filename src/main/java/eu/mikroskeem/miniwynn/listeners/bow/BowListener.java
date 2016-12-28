package eu.mikroskeem.miniwynn.listeners.bow;

import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BowListener implements Listener {
    @Inject private Server server;
    @Inject private Plugin plugin;
    private Map<Arrow,Integer> particleTasks = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(ProjectileHitEvent event){
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        if (entity instanceof Arrow){
            Arrow arrow = (Arrow) entity;
            if(arrow instanceof SpectralArrow){
                loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4.0F, false, false);
            }
            if(particleTasks.containsKey(arrow)){
                server.getScheduler().cancelTask(particleTasks.get(arrow));
            }
            arrow.remove();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(ProjectileLaunchEvent event){
        Projectile projectile = event.getEntity();
        if(projectile instanceof Arrow && projectile.getShooter() instanceof Player){
            final Arrow arrow = (Arrow) projectile;
            final int task = server.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
                arrow.getLocation().getWorld().spawnParticle(Particle.LAVA, arrow.getLocation(), 1);
            }, 0L, 1L);
            particleTasks.put(arrow, task);
        }
    }
}