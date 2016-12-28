package eu.mikroskeem.miniwynn.impl.weapons.bow;

import java.util.Arrays;
import java.util.List;

public class WaterBow extends AbstractBow {
    @Override
    public short getDamage() {
        return 1;
    }

    @Override
    public String getName() {
        return "§bWater Bow";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "Mighty water bow",
                "Makes u wet when you get hit"
        );
    }
}
