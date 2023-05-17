package it.thatskai.vanteykitpvp.listeners;

import it.thatskai.vanteykitpvp.manager.DefaultScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final DefaultScoreboardManager score = new DefaultScoreboardManager();
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        score.setMainScoreboard();
        score.updateScoreboard();
    }
}
