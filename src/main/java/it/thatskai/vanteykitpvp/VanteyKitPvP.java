package it.thatskai.vanteykitpvp;

import it.thatskai.vanteykitpvp.listeners.WeatherListener;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanteyKitPvP extends JavaPlugin {

    @Getter
    private static VanteyKitPvP instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new WeatherListener(), this);


    }
}
