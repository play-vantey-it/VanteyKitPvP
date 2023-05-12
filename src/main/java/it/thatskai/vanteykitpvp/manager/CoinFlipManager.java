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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CoinFlipManager {

    public List<String> players_list = getCoinFlip().getStringList("players-list");

    @SneakyThrows
    public void createFlip(Player p, int amount){
        String name = p.getName();

        players_list.add(name);
        getCoinFlip().set("players-list."+name+".coin", amount);
        saveCoinFlip();

        Economy.subtract(name, amount);

    }

    @SneakyThrows
    public void removeFlip(Player p){
        String name = p.getName();

        players_list.remove(name);
        Economy.add(name,getCoinFlip().getInt("players-list."+name+".coin") );
        getCoinFlip().set("players-list."+name+".coin", null);
        getCoinFlip().set("players-list", players_list);
        saveCoinFlip();


    }

    @SneakyThrows
    public void terminateFlip(Player p){
        String name = p.getName();

        players_list.remove(name);
        getCoinFlip().set("players-list."+name+".coin", null);
        getCoinFlip().set("players-list", players_list);
        saveCoinFlip();


    }

    public int getAmount(Player scommessionista){
        String name = scommessionista.getName();
        return getCoinFlip().getInt("players-list."+name+".coin");
    }

    public void openCoinFlipInventory(Player p){
        Inventory gui = Bukkit.createInventory(null, 54, Format.color("&6CoinFlip"));

        for(String players : players_list){

            if(getCoinFlip().getInt("players-list."+players+".coin") == 0){

            }else {

                ItemStack player = new ItemStack(Material.STAINED_CLAY);
                ItemMeta pmeta = player.getItemMeta();
                pmeta.setDisplayName(players);
                List<String> lore = new ArrayList<>();
                int amount = getCoinFlip().getInt("players-list." + players + ".coin");
                lore.add(Format.color(" "));
                lore.add(Format.color("&5Puntata: &7" + amount));
                lore.add("");
                lore.add(Format.color("&5Clicca per giocare con &7" + players));
                pmeta.setLore(lore);
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
                loser.sendMessage("Hai perso.");
                if(loser != coinflip){
                    Economy.subtract(loser.getName(), amount);
                }
            }else {
                winner.sendMessage("Hai perso.");
                if(winner != coinflip){
                    Economy.subtract(winner.getName(), amount);
                }

            }

            winnerfr.sendMessage("Hai vinto!");

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
