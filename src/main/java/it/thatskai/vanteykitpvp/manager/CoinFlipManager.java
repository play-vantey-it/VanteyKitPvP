package it.thatskai.vanteykitpvp.manager;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CoinFlipManager {

    public static List<String> players_list = new ArrayList<>();

    @SneakyThrows
    public void createFlip(Player p, int amount){
        String name = p.getName();

        players_list.add(name);
        getCoinFlip().set("players-list", players_list);
        getCoinFlip().set("players-list."+name+".coin", amount);
        saveCoinFlip();

        Economy.subtract(name, amount);

    }

    @SneakyThrows
    public void removeFlip(Player p){
        String name = p.getName();

        players_list.remove(name);
        getCoinFlip().set("players-list", players_list);
        Economy.add(name,getCoinFlip().getInt("players-list."+name+".coin") );
        getCoinFlip().set("players-list."+name+".coin", null);
        saveCoinFlip();


    }

    @SneakyThrows
    public void terminateFlip(Player p){
        String name = p.getName();

        players_list.remove(name);
        getCoinFlip().set("players-list", players_list);
        getCoinFlip().set("players-list."+name+".coin", null);
        saveCoinFlip();


    }

    public int getAmount(Player scommessionista){
        String name = scommessionista.getName();
        return getCoinFlip().getInt("players-list."+name+".coin");
    }

    public void openCoinFlipInventory(Player p){
        Inventory gui = Bukkit.createInventory(null, 54, Format.color(VanteyKitPvP.getInstance().getConfig().getString("gui-title")));

        for(String players : players_list){

            if(getCoinFlip().getInt("players-list."+players+".coin") != 0){
                ItemStack player = new ItemStack(Material.STAINED_CLAY);
                ItemMeta pmeta = player.getItemMeta();
                pmeta.setDisplayName(players);
                List<String> lore = VanteyKitPvP.getInstance().getConfig().getStringList("item-lore");
                List<String> lorefr = new ArrayList<>();
                int amount = getCoinFlip().getInt("players-list." + players + ".coin");
                for(String string : lore){
                    lorefr.add(Format.color(string
                            .replace("%player%", players)
                            .replace("%amount%", String.valueOf(amount))));
                }
                pmeta.setLore(lorefr);
                player.setItemMeta(pmeta);

                gui.addItem(player);
            }

        }
        p.openInventory(gui);
    }

    @SneakyThrows
    public void selectPlayer(Player winner, Player loser, Player coinflip) {


        if (winner != null && loser != null) {
            int indiceCasuale = (int) (Math.random() * 2);
            int amount = getAmount(coinflip);
            Player winnerfr = (indiceCasuale == 0) ? winner : loser;

            if(winnerfr == winner){
                loser.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("coinflip-lose")));
                if(loser != coinflip){
                    Economy.subtract(loser.getName(), amount);
                }
            }else {
                winner.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("coinflip-lose")));
                if(winner != coinflip){
                    Economy.subtract(winner.getName(), amount);
                }

            }

            winnerfr.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("coinflip-win")));

            Economy.add(winnerfr.getName(), amount*2);

            terminateFlip(coinflip);

        }

    }


    public static FileConfiguration getCoinFlip(){
        return VanteyKitPvP.coinflip;
    }


    public void saveCoinFlip(){
        VanteyKitPvP.saveCoinFlipConfig();
    }
}
