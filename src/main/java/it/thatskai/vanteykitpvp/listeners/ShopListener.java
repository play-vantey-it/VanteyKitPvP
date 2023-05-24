package it.thatskai.vanteykitpvp.listeners;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.manager.ShopManager;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

import static it.thatskai.vanteykitpvp.VanteyKitPvP.*;
public class ShopListener implements Listener {
    ShopManager shopm = new ShopManager();
    @SneakyThrows
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        ItemStack item = e.getCurrentItem();
        String main = Format.color(VanteyKitPvP.shop.getString("main.title"));
        if(e.getView().getTitle().equals(main)){
            e.setCancelled(true);
            List<String> items = new ArrayList<>();
            items.addAll(VanteyKitPvP.shop. getConfigurationSection("main.items").getKeys(false));
            for(String i : items){
                String name = Format.color(shop.getString("main.items."+i+".name"));
                String use = shop.getString("main.items."+i+".use");
                if(item.getItemMeta().getDisplayName().equals(name)){
                    if(use.equals("open pvp")){
                        shopm.openPvPShop(p);
                    }
                    if(use.equals("open potion")){
                        shopm.openPotionShop(p);
                    }
                    if(use.equals("open enchants")){
                        shopm.openEnchantShop(p);
                    }
                }
            }
            return;
        }
        String pvp = Format.color(VanteyKitPvP.shop.getString("pvp.title"));

        double balance = Economy.getMoney(p.getName());
        int price;


        if(e.getView().getTitle().equals(pvp)){
            e.setCancelled(true);
            List<String> items = new ArrayList<>();
            items.addAll(VanteyKitPvP.shop. getConfigurationSection("pvp.items").getKeys(false));
            for(String i : items){
                String name = Format.color(shop.getString("pvp.items."+i+".name"));
                String use = shop.getString("pvp.items."+i+".use");
                String mat = shop.getString("pvp.items."+i+".item");
                int amount = shop.getInt("pvp.items."+i+".amount");
                int data= shop.getInt("pvp.items."+i+".data");
                price = shop.getInt("pvp.items."+i+".price");


                if(item.getItemMeta().getDisplayName().equals(name)){
                    ItemStack n = new ItemStack(Material.getMaterial(mat), amount);
                    ItemMeta meta = n.getItemMeta();
                    n.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
                    n.setItemMeta(meta);

                    if(balance >= price){
                        Economy.subtract(p.getName(), price);

                        p.getInventory().addItem(n);

                    }else{
                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("not-enough-money")));
                    }

                }
            }
        }

        String potion = Format.color(VanteyKitPvP.shop.getString("potion.title"));

        if(e.getView().getTitle().equals(potion)){
            e.setCancelled(true);
            List<String> items = new ArrayList<>();
            items.addAll(VanteyKitPvP.shop. getConfigurationSection("potion.items").getKeys(false));
            for(String i : items){
                String name = Format.color(shop.getString("potion.items."+i+".name"));
                String use = shop.getString("potion.items."+i+".use");
                String mat = shop.getString("potion.items."+i+".item");
                int amount = shop.getInt("potion.items."+i+".amount");
                int data= shop.getInt("potion.items."+i+".data");
                price = shop.getInt("potion.items."+i+".price");


                if(item.getItemMeta().getDisplayName().equals(name)){
                    ItemStack n = new ItemStack(Material.getMaterial(mat), amount);
                    ItemMeta meta = n.getItemMeta();
                    n.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
                    n.setItemMeta(meta);

                    if(balance >= price){
                        Economy.subtract(p.getName(), price);

                        p.getInventory().addItem(n);

                    }else{
                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("not-enough-money")));
                    }

                }
            }
        }

        String enchants = Format.color(VanteyKitPvP.shop.getString("enchants.title"));

        if(e.getView().getTitle().equals(enchants)){
            e.setCancelled(true);
            List<String> items = new ArrayList<>();
            items.addAll(VanteyKitPvP.shop. getConfigurationSection("enchants.items").getKeys(false));
            for(String i : items){
                String name = Format.color(shop.getString("enchants.items."+i+".name"));
                String use = shop.getString("enchants.items."+i+".use");
                String mat = shop.getString("enchants.items."+i+".item");
                int amount = shop.getInt("enchants.items."+i+".amount");
                int data= shop.getInt("enchants.items."+i+".data");
                price = shop.getInt("enchants.items."+i+".price");

                int level = shop.getInt("enchants.items."+i+".enchant.level");
                String type = shop.getString("enchants.items."+i+".enchant.type");

                if(item.getItemMeta().getDisplayName().equals(name)){
                    ItemStack n = new ItemStack(Material.getMaterial(mat), amount);
                    ItemMeta meta = n.getItemMeta();
                    n.setData(new MaterialData(Material.getMaterial(mat), (byte) data));
                    meta.addEnchant(Enchantment.getByName(type), level , true);
                    n.setItemMeta(meta);

                    if(balance >= price){
                        Economy.subtract(p.getName(), price);

                        p.getInventory().addItem(n);

                    }else{
                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("not-enough-money")));
                    }

                }
            }
        }
    }
}
