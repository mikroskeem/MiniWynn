package eu.mikroskeem.miniwynn.game.impl.weapons.bow;

import com.google.inject.Inject;
import eu.mikroskeem.miniwynn.game.Spell;
import eu.mikroskeem.miniwynn.game.Weapon;
import eu.mikroskeem.miniwynn.game.impl.simple.SimpleSpell;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

import static eu.mikroskeem.miniwynn.game.Spell.MouseClick.LEFT;
import static eu.mikroskeem.miniwynn.game.Spell.MouseClick.RIGHT;


public abstract class AbstractBow implements Weapon {
    protected static Server server;
    protected static Plugin plugin;

    public static final Spell ESCAPE_SPELL = new SimpleSpell(Arrays.asList(LEFT, LEFT, LEFT), (player)-> {
        player.setVelocity(player.getVelocity().add(new Vector(0, 1, 0)));
    });
    public static final Spell RAPID_FIRE_SPELL = new SimpleSpell(Arrays.asList(LEFT, RIGHT, LEFT), (player)-> {
        final int task = server.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setKnockbackStrength(2);
        }, 0L, 2L);
        server.getScheduler().runTaskLater(plugin, ()->{
            server.getScheduler().cancelTask(task);
        }, 40L);
    });
    public static final Spell BOMB_SPELL = new SimpleSpell(Arrays.asList(LEFT, RIGHT, RIGHT), (player)->{
        SpectralArrow sp = player.launchProjectile(SpectralArrow.class);
        sp.setKnockbackStrength(1);
    });

    @Override public Material getMaterial() {
        return Material.BOW;
    }

    @Override public List<Spell> getSpells(){
        return Arrays.asList(ESCAPE_SPELL, RAPID_FIRE_SPELL, BOMB_SPELL);
    }

    @Inject private void set(Server server){
        AbstractBow.server = server;
    }
    @Inject private void set(Plugin plugin){
        AbstractBow.plugin = plugin;
    }
}