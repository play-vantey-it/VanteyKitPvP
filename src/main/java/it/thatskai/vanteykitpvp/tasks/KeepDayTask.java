package it.thatskai.vanteykitpvp.tasks;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class KeepDayTask extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getWorld(VanteyKitPvP.getInstance().getConfig().getString("world")).setTime(6000);

    }
}
