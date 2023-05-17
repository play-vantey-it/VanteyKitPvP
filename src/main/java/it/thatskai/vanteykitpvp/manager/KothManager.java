package it.thatskai.vanteykitpvp.manager;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.listeners.KothListener;
import it.thatskai.vanteykitpvp.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class KothManager {

    public ArrayList<Player> controlling = new ArrayList<>();

    public void startKoth(){
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("koth-start")));
        }
        inGame(true);
        setScoreboard();

        Bukkit.getServer().getScheduler().runTaskTimer(VanteyKitPvP.getInstance(), () -> {

            if (getState()) {
                setScoreboard();
            } else {
                cancelTask();
            }
        }, 0, 1);
    }

    public void inGame(boolean ingame){
        if(ingame){
            VanteyKitPvP.koth.set("state", "online");
            VanteyKitPvP.saveCoinFlipConfig();
        }else{
            VanteyKitPvP.koth.set("state", "offline");
            VanteyKitPvP.saveCoinFlipConfig();
        }
    }

    public boolean getState(){
        if(VanteyKitPvP.koth.getString("state").equals("online")){
            return true;
        }else{
            return false;
        }

    }

    public void giveKey(Player winner){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give physical koth 1 " + winner.getName());
        Bukkit.getLogger().info("La key KOTH e stata data a " + winner.getName());
    }

    public void stopKoth(){
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("koth-stop")));
        }
        controlling.clear();
        inGame(false);
        removeScoreboard();
    }

    public void forceStopKoth(){
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("koth-stop")));
        }
        controlling.clear();
        inGame(false);
        if(KothListener.timer != null){
            KothListener.timer.cancel();
        }
        KothListener.secondsRemaining = time();
        KothListener.currentPlayer = null;
        removeScoreboard();
    }


    public int time(){
        return VanteyKitPvP.getInstance().getConfig().getInt("koth-time");
    }

    public static void setScoreboard(){

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective(VanteyKitPvP.getInstance().getConfig().getString("scoreboard.koth.title"), "koth");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Format.color(VanteyKitPvP.getInstance().getConfig().getString("scoreboard.koth.title")));

        String player;

        if(KothListener.currentPlayer != null){
            player = KothListener.currentPlayer.getName();
        }else{
            player = "Nessuno";
        }


        int _score = VanteyKitPvP.getInstance().getConfig().getStringList("scoreboard.koth").size();

        for(String path : VanteyKitPvP.getInstance().getConfig().getStringList("scoreboard.koth.lines")){

            Score score = obj.getScore(Format.color(path
                    .replace("%time%", String.valueOf(KothListener.secondsRemaining))
                    .replace("%player_capturing%", player)
            ));

            score.setScore(_score +1);
            _score--;

        }



        for(Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(scoreboard);
        }
    }

    public void removeScoreboard(){
        for(Player all : Bukkit.getOnlinePlayers()){
            Scoreboard scoreboard = all.getScoreboard();
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);

            all.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public void cancelTask() {
        // Ferma il blocco di codice
        Bukkit.getServer().getScheduler().cancelTasks(VanteyKitPvP.getInstance());
    }

}
