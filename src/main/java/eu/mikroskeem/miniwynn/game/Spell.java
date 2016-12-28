package eu.mikroskeem.miniwynn.game;

import org.bukkit.entity.Player;

import java.util.List;

public interface Spell {
    List<MouseClick> getPattern();
    void cast(Player player);

    enum MouseClick {
        RIGHT,
        LEFT;

        @Override public String toString(){
            switch (this){
                case LEFT:
                    return "L";
                case RIGHT:
                    return "R";
                default:
                    return "";
            }
        }
    }
}
