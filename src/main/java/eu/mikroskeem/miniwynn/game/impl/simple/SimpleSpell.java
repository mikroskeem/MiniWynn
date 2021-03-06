package eu.mikroskeem.miniwynn.game.impl.simple;

import eu.mikroskeem.miniwynn.game.Spell;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SimpleSpell implements Spell {
    @Getter private final List<MouseClick> pattern;
    private final Callback callback;

    @Override
    public void cast(Player player) {
        callback.exec(player);
    }

    public interface Callback {
        void exec(Player player);
    }

    @Override
    public String toString() {
        return String.format("SimpleSpell(%s)", String.join(",", getPattern()
                .stream().map(Enum::toString).collect(Collectors.toList())));
    }
}
