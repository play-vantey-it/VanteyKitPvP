package it.thatskai.vanteykitpvp;

import it.thatskai.vanteykitpvp.commands.*;
import it.thatskai.vanteykitpvp.listeners.*;
import it.thatskai.vanteykitpvp.manager.CoinFlipManager;
import it.thatskai.vanteykitpvp.placeholders.KothPlaceholders;
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
    public static FileConfiguration koth;
    public static FileConfiguration envoy;


    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        createCoinFlipConfig();
        saveCoinFlipConfig();
        registerCommands();
        registerListener();

        if(coinflip.getConfigurationSection("player-list") != null){
            CoinFlipManager.players_list.addAll(coinflip.getConfigurationSection("players-list").getKeys(false));
        }


        BukkitTask daytask = new KeepDayTask().runTaskTimer(this, 0L, 0L);

        new KothPlaceholders().register();

    }

    @Override
    public void onDisable() {
        //KothManager koth = new KothManager();
        //koth.inGame(false);
    }

    public void registerCommands(){
        //getCommand("assegno").setExecutor(new AssegnoCommand());
        getCommand("coinflip").setExecutor(new CoinFlipCommand());
        getCommand("vkitpvp").setExecutor(new MainCommand());
        //getCommand("envoy").setExecutor(new EnvoyCommand());
        getCommand("koth").setExecutor(new KothCommand());
    }

    public void registerListener(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new WeatherListener(), this);
        //pm.registerEvents(new AssegnoListener(), this);
        pm.registerEvents(new CoinFlipListener(), this);
        pm.registerEvents(new KothListener(), this);
        pm.registerEvents(new JoinListener(), this);
        pm.registerEvents(new BlockListener(), this);
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

        configFile = new File(instance.getDataFolder(), "envoy.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            instance.saveResource("envoy.yml", false);
        }

        envoy = new YamlConfiguration();

        try {
            envoy.load(configFile);
        } catch (InvalidConfigurationException | IOException var1) {
            var1.printStackTrace();
        }

        configFile = new File(instance.getDataFolder(), "koth.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            instance.saveResource("koth.yml", false);
        }

        koth = new YamlConfiguration();

        try {
            koth.load(configFile);
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

        try {
            envoy.save(configFile);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

        try {
            koth.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
