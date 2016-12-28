package eu.mikroskeem.miniwynn.listeners;

import com.google.inject.Inject;
import eu.mikroskeem.miniwynn.ItemFactory;
import eu.mikroskeem.miniwynn.impl.weapons.bow.AirBow;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    @Inject private ItemFactory itemFactory;

    @EventHandler
    public void on(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);

        ItemStack item = itemFactory.getWeapon(AirBow.class);
        player.getInventory().setItemInMainHand(item);
    }
}
