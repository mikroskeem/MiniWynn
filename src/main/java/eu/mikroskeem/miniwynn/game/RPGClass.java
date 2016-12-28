package eu.mikroskeem.miniwynn.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;

@RequiredArgsConstructor
@Getter
public enum RPGClass {
    ARCHER("Archer", Spell.MouseClick.LEFT, Material.BOW, Arrow.class),
    MAGE("Mage", Spell.MouseClick.RIGHT, Material.STICK),
    WARRIOR("Warrior", Spell.MouseClick.RIGHT, Material.IRON_HOE),
    ASSASSIN("Assassin", Spell.MouseClick.RIGHT, Material.SHEARS);

    private final String name;
    private final Spell.MouseClick spellStartClick;
    private final Material weaponMaterial;

    private Class<? extends Projectile> projectile = null;

    RPGClass(String name, Spell.MouseClick spellStartClick, Material weaponMaterial,
                    Class<? extends Projectile> projectile){
        this.name = name;
        this.spellStartClick = spellStartClick;
        this.weaponMaterial = weaponMaterial;
        this.projectile = projectile;
    }
}