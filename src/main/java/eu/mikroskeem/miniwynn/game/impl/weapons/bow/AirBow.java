package eu.mikroskeem.miniwynn.game.impl.weapons.bow;

import java.util.Arrays;
import java.util.List;

public class AirBow extends AbstractBow {
    @Override
    public short getDamage() {
        return 2;
    }

    @Override
    public String getName() {
        return "§7§lAir Bow";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "§7Mighty air bow",
                "§7Makes you feel some fresh air blow"
        );
    }
}
