package eu.mikroskeem.miniwynn.impl;

import eu.mikroskeem.miniwynn.ItemFactory;
import eu.mikroskeem.miniwynn.MinyWynnPlugin;
import eu.mikroskeem.miniwynn.Spell;
import eu.mikroskeem.miniwynn.Weapon;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SimpleItemFactory implements ItemFactory {
    private final Map<Class<? extends Weapon>, Weapon> weaponList = new LinkedHashMap<>();
    private final Map<List<Spell.MouseClick>, Spell> patternCache = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends Weapon> ItemStack getWeapon(Class<T> weapon){
        T weaponInst = (T) weaponList.putIfAbsent(weapon, MinyWynnPlugin.getInjector().getInstance(weapon));
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
        /* TODO: there is better solution, but idgaf at the moment */
        if(patternCache.containsKey(casts)){
            return patternCache.get(casts);
        } else {
            for (Weapon weapon : weaponList.values()) {
                List<Spell> spells = weapon.getSpells();
                for (Spell spell : spells) {
                    if (spell.getPattern().equals(casts)) {
                        patternCache.put(casts, spell);
                        return spell;
                    }
                }
            }
            return null;
        }
    }
}
