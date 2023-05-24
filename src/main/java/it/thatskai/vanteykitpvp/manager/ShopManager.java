package it.thatskai.vanteykitpvp.manager;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

import static it.thatskai.vanteykitpvp.VanteyKitPvP.*;
public class ShopManager {
    private final VanteyKitPvP van = VanteyKitPvP.getInstance();
    public void openMainShop(Player p){
        Inventory main = Bukkit.createInventory(
                null,
                VanteyKitPvP.shop.getInt("main.size"),
                Format.color(VanteyKitPvP.shop.getString("main.title")));

        List<String> items = new ArrayList<>();
        items.addAll(VanteyKitPvP.shop. getConfigurationSection("main.items").getKeys(false));



        for(String item : items){
            ItemStack i = new ItemStack(Material.getMaterial(item));
            ItemMeta meta = i.getItemMeta();
            meta.setDisplayName(Format.color(VanteyKitPvP.shop.getString("main.items."+item+".name")));
            i.setItemMeta(meta);
            int slot =  VanteyKitPvP.shop.getInt("main.items."+item+".slot");
            main.setItem(slot, i);
        }

        p.openInventory(main);
    }

    public void openPvPShop(Player p){
        Inventory pvp = Bukkit.createInventory(
                null,
                VanteyKitPvP.shop.getInt("pvp.size"),
                Format.color(VanteyKitPvP.shop.getString("pvp.title")));

        List<String> items = new ArrayList<>();
        items.addAll(VanteyKitPvP.shop. getConfigurationSection("pvp.items").getKeys(false));


        for(String item : items){
            int amount = shop.getInt("pvp.items."+item+".amount");
            String name = Format.color(VanteyKitPvP.shop.getString("pvp.items."+item+".name"));
            int data = shop.getInt("pvp.items."+item+".data");
            int slot =  VanteyKitPvP.shop.getInt("pvp.items."+item+".slot");
            String mat = shop.getString("pvp.items."+item+".item");

            ItemStack i = new ItemStack(Material.getMaterial(mat), amount);
            ItemMeta meta = i.getItemMeta();
            i.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
            meta.setDisplayName(name);
            i.setItemMeta(meta);
            pvp .setItem(slot, i);
        }


        p.openInventory(pvp);
    }

    public void openPotionShop(Player p){
        Inventory potion = Bukkit.createInventory(
                null,
                VanteyKitPvP.shop.getInt("potion.size"),
                Format.color(VanteyKitPvP.shop.getString("potion.title")));

        List<String> items = new ArrayList<>();
        items.addAll(VanteyKitPvP.shop. getConfigurationSection("potion.items").getKeys(false));



        for(String item : items){
            int amount = shop.getInt("potion.items."+item+".amount");
            String name = Format.color(VanteyKitPvP.shop.getString("potion.items."+item+".name"));
            int data = shop.getInt("potion.items."+item+".data");
            int slot =  VanteyKitPvP.shop.getInt("potion.items."+item+".slot");
            String mat = shop.getString("potion.items."+item+".item");

            ItemStack i = new ItemStack(Material.getMaterial(mat), amount);
            ItemMeta meta = i.getItemMeta();
            i.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
            meta.setDisplayName(name);
            i.setItemMeta(meta);
            potion.setItem(slot, i);
        }


        p.openInventory(potion);
    }

    public void openEnchantShop(Player p){
        Inventory enchants = Bukkit.createInventory(
                null,
                VanteyKitPvP.shop.getInt("enchants.size"),
                Format.color(VanteyKitPvP.shop.getString("enchants.title")));

        List<String> items = new ArrayList<>();
        items.addAll(VanteyKitPvP.shop. getConfigurationSection("enchants.items").getKeys(false));

        for(String item : items){
            int amount = shop.getInt("enchants.items."+item+".amount");
            String name = Format.color(VanteyKitPvP.shop.getString("enchants.items."+item+".name"));
            int data = shop.getInt("enchants.items."+item+".data");
            int slot =  VanteyKitPvP.shop.getInt("enchants.items."+item+".slot");
            String mat = shop.getString("enchants.items."+item+".item");

            ItemStack i = new ItemStack(Material.getMaterial(mat), amount);
            ItemMeta meta = i.getItemMeta();
            i.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
            meta.setDisplayName(name);
            i.setItemMeta(meta);
            enchants.setItem(slot, i);
        }


        p.openInventory(enchants);
    }

}
