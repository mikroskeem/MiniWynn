package eu.mikroskeem.miniwynn;

import org.bukkit.entity.Player;

import java.util.List;

public interface Spell {
    List<MouseClick> getPattern();
    void cast(Player player);

    enum MouseClick {
        RIGHT,
        LEFT
    }
}
