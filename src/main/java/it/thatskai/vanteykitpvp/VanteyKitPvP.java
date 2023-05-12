package it.thatskai.vanteykitpvp;

import it.thatskai.vanteykitpvp.commands.AssegnoCommand;
import it.thatskai.vanteykitpvp.commands.CoinFlipCommand;
import it.thatskai.vanteykitpvp.listeners.AssegnoListener;
import it.thatskai.vanteykitpvp.listeners.WeatherListener;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class VanteyKitPvP extends JavaPlugin {

    @Getter
    private static VanteyKitPvP instance;
    public static File configFile;
    public static FileConfiguration coinflip;


    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        createCoinFlipConfig();
        saveCoinFlipConfig();
        registerCommands();
        registerListener();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        getCommand("assegno").setExecutor(new AssegnoCommand());
        getCommand("coinflip").setExecutor(new CoinFlipCommand());
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new AssegnoListener(), this);
    }

    public static void createCoinFlipConfig() {
        configFile = new File(instance.getDataFolder(), "coinflip.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            instance.saveResource("coinflip.yml", false);
        }

        coinflip = new YamlConfiguration();

        try {
            coinflip.load(configFile);
        } catch (InvalidConfigurationException | IOException var1) {
            var1.printStackTrace();
        }


    }

    public static void saveCoinFlipConfig() {
        try {
            coinflip.save(configFile);
        } catch (IOException var1) {
            var1.printStackTrace();
        }


    }
}
