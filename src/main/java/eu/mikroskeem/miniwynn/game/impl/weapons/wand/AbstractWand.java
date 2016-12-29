package eu.mikroskeem.miniwynn.game.impl.weapons.wand;

import eu.mikroskeem.miniwynn.game.Spell;
import eu.mikroskeem.miniwynn.game.Weapon;
import eu.mikroskeem.miniwynn.game.impl.simple.SimpleSpell;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

import static eu.mikroskeem.miniwynn.game.Spell.MouseClick.RIGHT;

public abstract class AbstractWand implements Weapon {
    public static final Spell TELEPORT_SPELL = new SimpleSpell(Arrays.asList(RIGHT, RIGHT, RIGHT), (player)->{

    });

    //public static final Spell METEOR_SPELL = new SimpleSpell(Arrays.asList(), (player)->{});

    @Override
    public Material getMaterial() {
        return Material.STICK;
    }

    @Override
    public List<Spell> getSpells() {
        return Arrays.asList(TELEPORT_SPELL);
    }
}
