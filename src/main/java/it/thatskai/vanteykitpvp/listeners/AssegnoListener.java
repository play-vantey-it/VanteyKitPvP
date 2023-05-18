package it.thatskai.vanteykitpvp.listeners;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AssegnoListener implements Listener {
    @SneakyThrows
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        Material mat = e.getMaterial();
        ItemStack i = e.getPlayer().getItemInHand();
        List<String> lore = new ArrayList<>();
        lore.add("§7Tasto destro per riscattare");

        if(a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)){

            if(i.getItemMeta() == null) return;
            if(i.getItemMeta().getLore() == null) return;
            if(i.getItemMeta().getDisplayName() == null) return;

            if(mat.equals(Material.PAPER) && i.getItemMeta().getLore().equals(lore) && i.getItemMeta().getDisplayName().endsWith("$")){
                String name = e.getPlayer().getItemInHand().getItemMeta().getDisplayName();
                String name_without_$ = name.replaceAll("[^\\d]", "");
                int amount = Integer.parseInt(name_without_$);

                Economy.add(p.getName(), amount);

                if(i.getAmount() > 1){
                    i.setAmount(i.getAmount()-1);
                }else{
                    ItemStack air = new ItemStack(Material.AIR);

                    p.getInventory().setItemInHand(air);
                }

                p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("assegno-riscattato")
                        .replace("%amount%", String.valueOf(amount))));

                return;
            }

        }
    }
}
