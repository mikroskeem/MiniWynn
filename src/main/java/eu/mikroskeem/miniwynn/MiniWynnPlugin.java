package eu.mikroskeem.miniwynn;

import com.google.inject.Injector;
import eu.mikroskeem.miniwynn.impl.SimpleItemFactory;
import eu.mikroskeem.providerslib.api.Providers;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class MiniWynnPlugin extends JavaPlugin {
    @Getter private static Injector injector;
    private SimpleItemFactory itemFactory;

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
            binder.bind(ItemFactory.class).to(SimpleItemFactory.class);
        });
    }
}
