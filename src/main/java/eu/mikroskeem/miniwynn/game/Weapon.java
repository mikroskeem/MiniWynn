package eu.mikroskeem.miniwynn.game;

import org.bukkit.Material;

import java.util.List;

public interface Weapon {
    Material getMaterial();
    short getDamage(); // Will be used to determine item design
    String getName();
    List<String> getLore();
    List<Spell> getSpells();
}
