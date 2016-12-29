package eu.mikroskeem.miniwynn.game.impl.weapons.wand;

import java.util.Arrays;
import java.util.List;

public class WaterWand extends AbstractWand {
    @Override
    public short getDamage() {
        return 1;
    }

    @Override
    public String getName() {
        return "§b§lWater Wand";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "§7Mighty water wand",
                "§7Makes you wet"
        );
    }
}
