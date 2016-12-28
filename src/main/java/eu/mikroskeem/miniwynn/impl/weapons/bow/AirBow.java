package eu.mikroskeem.miniwynn.impl.weapons.bow;

import java.util.Arrays;
import java.util.List;

public class AirBow extends AbstractBow {
    @Override
    public short getDamage() {
        return 2;
    }

    @Override
    public String getName() {
        return "§7Air Bow";
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "Mighty air bow",
                "Makes you feel some fresh air blow"
        );
    }
}
