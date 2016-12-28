package eu.mikroskeem.miniwynn.listeners;

import com.google.inject.Inject;
import eu.mikroskeem.miniwynn.ItemFactory;
import eu.mikroskeem.miniwynn.Spell;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpellListener implements Listener {
    @Inject private ItemFactory itemFactory;

    private final Map<Player, List<Spell.MouseClick>> casts = new LinkedHashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerAnimationEvent animationEvent){
        Player player = animationEvent.getPlayer();
        on(player, Spell.MouseClick.LEFT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) return;

        if(
                event.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            on(player, Spell.MouseClick.RIGHT);
        }
    }

    @EventHandler
    public void on(PlayerQuitEvent event){
        if(casts.containsKey(event.getPlayer())){
            casts.remove(event.getPlayer());
        }
    }

    private void on(Player player, Spell.MouseClick click){
        List<Spell.MouseClick> clicks = casts.computeIfAbsent(player, k -> new LinkedList<>());
        casts.putIfAbsent(player, clicks);
        log.debug("Player {} did {}", player.getName(), click);
        clicks.add(click);
        if(clicks.size() > 2){
            /* Should cast a spell */
            Spell spell = itemFactory.getSpell(clicks);
            if(spell != null){
                log.debug("Player {} is casting spell {}", player.getName(), spell);
                spell.cast(player);
            } else {
                log.warn("Player casted unknown spell! {}", clicks);
            }
            clicks.clear();
        }
    }
}
