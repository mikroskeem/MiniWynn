package eu.mikroskeem.miniwynn.listeners;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import eu.mikroskeem.miniwynn.game.ItemFactory;
import eu.mikroskeem.miniwynn.game.PlayerManager;
import eu.mikroskeem.miniwynn.game.RPGClass;
import eu.mikroskeem.miniwynn.game.Spell;
import eu.mikroskeem.providerslib.api.Title;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SpellListener implements Listener {
    @Inject private ItemFactory itemFactory;
    @Inject private PlayerManager playerManager;
    @Inject private Title title;

    /* TODO: quick and easy, but inefficient? */
    Cache<Player, List<Spell.MouseClick>> casts = Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build();

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
        /*if(casts.containsKey(event.getPlayer())){
            casts.remove(event.getPlayer());
        }*/
    }

    private void on(Player player, Spell.MouseClick click){
        if(!validate(player)) return;
        ItemStack handItem = player.getInventory().getItemInMainHand();
        RPGClass rpgClass = playerManager.getPlayerClass(player);
        if(handItem != null && handItem.getType() == rpgClass.getWeaponMaterial()) {
            List<Spell.MouseClick> clicks = casts.get(player, k -> new LinkedList<>());
            casts.put(player, clicks);

            log.debug("Player {} did click {}", player.getName(), click);
            log.debug("Clicks[] size: {}", clicks.size());

            int size = clicks.size();
            if(size == 0 && rpgClass.getSpellStartClick() != click){
                log.debug("{} clicked attack button", player.getName());
                if (rpgClass.getProjectile() != null) {
                    player.launchProjectile(rpgClass.getProjectile());
                }
            } else if(size == 0 && rpgClass.getSpellStartClick() == click){
                clicks.add(rpgClass.getSpellStartClick());
                casts.put(player, clicks);
                title.sendTitle(player, 0, 20, 20, "", formatSpells(clicks));
            } else if(size < 2 && size > 0){
                clicks.add(click);
                casts.put(player, clicks);
                title.sendTitle(player, 0, 20, 20, "", formatSpells(clicks));
            } else if(size == 2){
                /* Should cast a spell */
                clicks.add(click);
                title.sendTitle(player, 0, 10, 10, "", formatSpells(clicks));
                Spell spell = itemFactory.getSpell(clicks);
                if (spell != null) {
                    log.debug("Player {} is casting spell {}", player.getName(), spell);
                    spell.cast(player);
                } else {
                    log.warn("Player casted unknown spell! {}", clicks);
                }
                clicks.clear();
            }
        }
    }

    private boolean validate(Player player){
        if(player.getGameMode().equals(GameMode.ADVENTURE)){
            return true;
        }
        return false;
    }

    private String formatSpells(List<Spell.MouseClick> clicks){
        /* TODO: it works and I know there is better way, but I'm just lazy */
        switch (clicks.size()){
            case 1:
                return String.format("§a%s§8-§7?§8-§7?", clicks.get(0));
            case 2:
                return String.format("§a%s§8-§a%s§8-§7?", clicks.get(0), clicks.get(1));
            case 3:
                return String.format("§a%s§8-§a%s§8-§a%s", clicks.get(0), clicks.get(1), clicks.get(2));
            default:
                return "";
        }
    }
}