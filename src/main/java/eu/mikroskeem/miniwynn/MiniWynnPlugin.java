package eu.mikroskeem.miniwynn;

import com.google.inject.Injector;
import eu.mikroskeem.miniwynn.game.ItemFactory;
import eu.mikroskeem.miniwynn.game.PlayerManager;
import eu.mikroskeem.miniwynn.game.impl.simple.SimpleItemFactory;
import eu.mikroskeem.miniwynn.game.impl.simple.SimplePlayerManager;
import eu.mikroskeem.miniwynn.listeners.PlayerListener;
import eu.mikroskeem.miniwynn.listeners.SpellListener;
import eu.mikroskeem.miniwynn.listeners.bow.BowListener;
import eu.mikroskeem.providerslib.api.Providers;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class MiniWynnPlugin extends JavaPlugin {
    @Getter private static Injector injector;
    private ItemFactory itemFactory;

    private List<Class<? extends Listener>> listeners = Arrays.asList(
            PlayerListener.class,
            SpellListener.class,
            BowListener.class
    );

    @Override public void onEnable() {
        Injector injector;

        log.info("Enabling plugin");
        try {
            RegisteredServiceProvider<Providers> rsp = checkNotNull(getServer().getServicesManager()
                    .getRegistration(Providers.class));
            injector = checkNotNull(rsp.getProvider()).getInjector();
        } catch (NullPointerException e){
            log.error("Failed to get ProvidersLib providers");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        itemFactory = injector.getInstance(SimpleItemFactory.class);
        MiniWynnPlugin.injector = injector.createChildInjector(binder -> {
            binder.bind(Server.class).toInstance(getServer());
            binder.bind(Plugin.class).toInstance(this);
            binder.bind(ItemFactory.class).toInstance(itemFactory);
            binder.bind(PlayerManager.class).to(SimplePlayerManager.class).asEagerSingleton();
        });

        listeners.forEach(listener->{
            Listener listenerInst = getInjector().getInstance(listener);
            getServer().getPluginManager().registerEvents(listenerInst, this);
        });
    }
}
