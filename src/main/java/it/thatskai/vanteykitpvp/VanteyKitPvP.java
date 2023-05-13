package it.thatskai.vanteykitpvp;

import it.thatskai.vanteykitpvp.commands.AssegnoCommand;
import it.thatskai.vanteykitpvp.commands.CoinFlipCommand;
import it.thatskai.vanteykitpvp.commands.MainCommand;
import it.thatskai.vanteykitpvp.listeners.AssegnoListener;
import it.thatskai.vanteykitpvp.listeners.CoinFlipListener;
import it.thatskai.vanteykitpvp.listeners.WeatherListener;
import it.thatskai.vanteykitpvp.manager.CoinFlipManager;
import it.thatskai.vanteykitpvp.tasks.KeepDayTask;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

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

        CoinFlipManager.players_list.addAll(coinflip.getConfigurationSection("players-list").getKeys(false));

        BukkitTask daytask = new KeepDayTask().runTaskTimer(this, 0L, 0L);

    }

    public void registerCommands(){
        getCommand("assegno").setExecutor(new AssegnoCommand());
        getCommand("coinflip").setExecutor(new CoinFlipCommand());
        getCommand("vkitpvp").setExecutor(new MainCommand());
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new WeatherListener(), this);
        pm.registerEvents(new AssegnoListener(), this);
        pm.registerEvents(new CoinFlipListener(), this);
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
            //coinflip.set("players-list", CoinFlipManager.players_list);
            coinflip.save(configFile);
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }
}
