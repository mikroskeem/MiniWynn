package eu.mikroskeem.miniwynn.game.impl.weapons.wand;

import java.util.Arrays;
import java.util.List;

public class AirWand extends AbstractWand {
    @Override
    public short getDamage() {
        return 2;
    }

    @Override
    public String getName() {
        return "§7§lAir Wand";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "§7Mighty air wand",
                "§7Makes you feel some fresh air blow"
        );
    }
}
