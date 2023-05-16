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
    private BukkitRunnable timer;
    private Player currentPlayer;

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        if(!koth.getState()) return;


        Block blockBelow = loc.subtract(0, 1, 0).getBlock();

        if (blockBelow.getType() == Material.WOOL) {
            byte data = blockBelow.getData();

            if (data == 14 || data == 0) {
                if (currentPlayer == null) {
                    startTimer(player);
                }else
                if(currentPlayer == player) return;
            } else {
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

    public void startTimer(Player player) {
        if (timer != null) {
            timer.cancel();
        }

        currentPlayer = player;
        Bukkit.broadcastMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("start-controlling")
                .replace("%player%", currentPlayer.getName())));

        timer = new BukkitRunnable() {
            @Override
            public void run() {

                Bukkit.broadcastMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("capture")
                        .replace("%player%", currentPlayer.getName())));
                koth.stopKoth();
                koth.giveKey(currentPlayer);
                currentPlayer = null;
                timer = null;
            }
        };

        timer.runTaskLater(VanteyKitPvP.getInstance(), VanteyKitPvP.getInstance().getConfig().getInt("koth-time"));
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }

        Bukkit.broadcastMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("stop-controlling")
                .replace("%player%", currentPlayer.getName())));

        currentPlayer = null;
        timer = null;
    }
}
