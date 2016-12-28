package eu.mikroskeem.miniwynn.game.impl.simple;

import eu.mikroskeem.miniwynn.game.ItemFactory;
import eu.mikroskeem.miniwynn.MiniWynnPlugin;
import eu.mikroskeem.miniwynn.game.Spell;
import eu.mikroskeem.miniwynn.game.Weapon;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Slf4j
public class SimpleItemFactory implements ItemFactory {
    private final HashMap<Class<? extends Weapon>, Weapon> weaponCache = new LinkedHashMap<>();
    private final HashMap<List<Spell.MouseClick>, Spell> spellCache = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Weapon> ItemStack getWeapon(Class<T> weapon){
        T weaponInst = (T) weaponCache.computeIfAbsent(weapon, k->MiniWynnPlugin.getInjector().getInstance(weapon));
        weaponInst.getSpells().forEach(spell -> spellCache.put(spell.getPattern(), spell));
        weaponCache.putIfAbsent(weapon, weaponInst);
        ItemStack item = new ItemStack(
                weaponInst.getMaterial(),
                1,
                weaponInst.getDamage()
        );
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName(weaponInst.getName());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.setLore(weaponInst.getLore());
        item.setItemMeta(meta);
        return item;
    }

    public Spell getSpell(List<Spell.MouseClick> casts) {
        return spellCache.get(casts);
    }
}
