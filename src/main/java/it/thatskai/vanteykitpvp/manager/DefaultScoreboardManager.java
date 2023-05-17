package it.thatskai.vanteykitpvp.manager;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.listeners.KothListener;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class DefaultScoreboardManager {
    //private KothManager koth = new KothManager();
    public void updateScoreboard(){
        KothManager koth = new KothManager();
        Bukkit.getServer().getScheduler().runTaskTimer(VanteyKitPvP.getInstance(), () -> {

            if (!koth.getState()) {
                setMainScoreboard();
            } else {
                cancelTask();
            }
        }, 0, 1);
    }
    @SneakyThrows
    public void setMainScoreboard(){

        for(Player all : Bukkit.getOnlinePlayers()){
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective obj = scoreboard.registerNewObjective(VanteyKitPvP.getInstance().getConfig().getString("scoreboard.default.title"), "default");

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName(Format.color(VanteyKitPvP.getInstance().getConfig().getString("scoreboard.default.title")));


            String kills = PlaceholderAPI.setPlaceholders(all, "%kitpvp_kills%");
            String deaths = PlaceholderAPI.setPlaceholders(all, "%kitpvp_deaths%");
            String streak = PlaceholderAPI.setPlaceholders(all, "%kitpvp_streak%");

            String money = PlaceholderAPI.setPlaceholders(all, "%vault_eco_balance_formatted%");

            //conditions
            String normal_snowball = PlaceholderAPI.setPlaceholders(all, "%kitpvp_snowball%");
            String snowball_condition = null;

            if(normal_snowball == "0.0"){
                snowball_condition = "";
            }else{
                snowball_condition = "&fMiopia: &c"+normal_snowball+"s";
            }

            String normal_creeper = PlaceholderAPI.setPlaceholders(all, "%kitpvp_creeper_egg%");
            String creeper_condition = null;

            if(normal_creeper == "0.0"){
                creeper_condition = "";
            }else{
                creeper_condition = "&fCreeper: &a"+normal_creeper;
            }

            String normal_rod = PlaceholderAPI.setPlaceholders(all, "%kitpvp_grappling_hook%");
            String rod_condition = null;

            if(normal_rod == "0.0"){
                rod_condition = "";
            }else{
                rod_condition = "&fRampino: &b"+normal_rod+"s";
            }

            String balance = String.valueOf(Economy.format(Economy.getMoney(all.getName())));



            int _score = VanteyKitPvP.getInstance().getConfig().getStringList("scoreboard.default").size();

            for(String path : VanteyKitPvP.getInstance().getConfig().getStringList("scoreboard.default.lines")){

                Score score = obj.getScore(Format.color(path
                        .replace("%condition:snowball%", snowball_condition)
                        .replace("%condition:creeper%", creeper_condition)
                        .replace("%condition:rod%", rod_condition)
                        .replace("%balance%",balance)
                ));

                score.setScore(_score +1);
                _score--;

            }

            if(all == null) return;

            all.setScoreboard(scoreboard);
        }



    }

    public void removeMainScoreboard(){
        for(Player all : Bukkit.getOnlinePlayers()){
            Scoreboard scoreboard = all.getScoreboard();
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);

            all.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public void cancelTask() {
        Bukkit.getServer().getScheduler().cancelTasks(VanteyKitPvP.getInstance());
    }
}
