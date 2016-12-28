package eu.mikroskeem.miniwynn.listeners.bow;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class BowListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(ProjectileHitEvent event){
        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        if(entity instanceof SpectralArrow){
            loc.getWorld().createExplosion(loc, 4.0F);
        }
        if (entity instanceof Arrow){
            entity.remove();
        }
    }
}