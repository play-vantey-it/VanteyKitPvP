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

public class CoinFlipManager {

    private final List<String> players_list = getCoinFlip().getStringList("players-list");

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
        saveCoinFlip();


    }

    public void openCoinFlipInventory(Player p){
        Inventory gui = Bukkit.createInventory(null, 54, Format.color("&6CoinFlip"));

        for(String players : players_list){

            ItemStack player = new ItemStack(Material.STAINED_CLAY);
            ItemMeta pmeta = player.getItemMeta();
            pmeta.setDisplayName(players);
            List<String> lore = new ArrayList<>();
            int amount = getCoinFlip().getInt("players-list."+players+".coin");
            lore.add(Format.color(" "));
            lore.add(Format.color("&5Puntata: &7" + amount));
            lore.add("");
            lore.add(Format.color("&5Clicca per giocare con &7" + players));
            pmeta.setLore(lore);
            player.setItemMeta(pmeta);

            gui.addItem(player);
        }






        p.openInventory(gui);
    }





    public FileConfiguration getCoinFlip(){
        return VanteyKitPvP.coinflip;
    }

    public void saveCoinFlip(){
        VanteyKitPvP.saveCoinFlipConfig();
    }
}
