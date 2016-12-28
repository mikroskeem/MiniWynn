package eu.mikroskeem.miniwynn.game.impl.weapons.bow;

import java.util.Arrays;
import java.util.List;

public class WaterBow extends AbstractBow {
    @Override
    public short getDamage() {
        return 1;
    }

    @Override
    public String getName() {
        return "§b§lWater Bow";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "§7Mighty water bow",
                "§7Makes u wet when you get hit"
        );
    }
}
