package eu.mikroskeem.miniwynn.game.impl.simple;

import eu.mikroskeem.miniwynn.game.PlayerManager;
import eu.mikroskeem.miniwynn.game.RPGClass;
import org.bukkit.entity.Player;

public class SimplePlayerManager implements PlayerManager {
    @Override
    public RPGClass getPlayerClass(Player player) {
        /* Everyone is archer! */
        return RPGClass.ARCHER;
    }
}
