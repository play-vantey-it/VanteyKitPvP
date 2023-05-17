package it.thatskai.vanteykitpvp.listeners;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.manager.KothManager;
import it.thatskai.vanteykitpvp.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class KothListener implements Listener {

    private final KothManager koth = new KothManager();
    public static BukkitRunnable timer;
    public static Player currentPlayer;
    public static int secondsRemaining;

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        if(!koth.getState()) return;


        Block blockBelow = loc.subtract(0, 1, 0).getBlock();

        Block solidBlock = findSolidBlock(loc);
        if(solidBlock == null);
        if(solidBlock.getType() == null);

        if (blockBelow.getType() == Material.WOOL || solidBlock.getType() == Material.WOOL) {
            byte data = blockBelow.getData();
            byte data2 = solidBlock.getData();

            if (data == 14 || data == 0 || data2 == 14 || data2 == 0) {
                if (currentPlayer == null) {
                    startTimer(player);
                }else
                if(currentPlayer == player) return;
            }else {
                if (currentPlayer != null && currentPlayer.equals(player)) {
                    stopTimer();
                }
            }
        } else {
            if (currentPlayer != null && currentPlayer.equals(player)) {
                stopTimer();
            }
        }
    }
    private Block findSolidBlock(Location location) {
        Block block = location.getBlock();

        while (block.getType() == Material.AIR && block.getY() > 0) {
            block = block.getLocation().subtract(0, 1, 0).getBlock();
        }

        return block.getType() != Material.AIR ? block : null;
    }

    public void startTimer(Player player) {
        if (timer != null) {
            timer.cancel();
        }

        currentPlayer = player;
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("start-controlling")
                    .replace("%player%", currentPlayer.getName())));
        }

        secondsRemaining = koth.time();
        timer = new BukkitRunnable() {
            @Override
            public void run() {

                secondsRemaining--;

                if(currentPlayer != player){
                    cancel();
                }

                if(secondsRemaining == 0){
                    cancel();
                    secondsRemaining = koth.time();
                    for(Player all : Bukkit.getOnlinePlayers()){
                        all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("capture")
                                .replace("%player%", currentPlayer.getName())));
                    }
                    koth.stopKoth();
                    koth.giveKey(currentPlayer);
                    currentPlayer = null;
                    timer = null;
                }
            }
        };

        timer.runTaskTimer(VanteyKitPvP.getInstance(),0, 20L);


    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            secondsRemaining = koth.time();
        }

        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("stop-controlling")
                    .replace("%player%", currentPlayer.getName())));
        }

        currentPlayer = null;
        timer = null;
    }
}
