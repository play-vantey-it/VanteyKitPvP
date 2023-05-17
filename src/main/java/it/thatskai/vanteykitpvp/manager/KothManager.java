package it.thatskai.vanteykitpvp.manager;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.listeners.KothListener;
import it.thatskai.vanteykitpvp.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class KothManager {

    public ArrayList<Player> controlling = new ArrayList<>();

    public void startKoth(){
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("koth-start")));
        }
        inGame(true);
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
    }

    public void forceStopKoth(){
        for(Player all : Bukkit.getOnlinePlayers()){
            all.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("koth-stop")));
        }
        controlling.clear();
        inGame(false);
        KothListener.timer.cancel();
        KothListener.secondsRemaining = time();
        KothListener.currentPlayer = null;
    }


    public int time(){
        return VanteyKitPvP.getInstance().getConfig().getInt("koth-time");
    }

}
