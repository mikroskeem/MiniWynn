package eu.mikroskeem.miniwynn.game;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ItemFactory {
    <T extends Weapon> ItemStack getWeapon(Class<T> weapon);
    Spell getSpell(List<Spell.MouseClick> casts);
}
