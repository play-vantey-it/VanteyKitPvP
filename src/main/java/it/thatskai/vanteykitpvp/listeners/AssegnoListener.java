package it.thatskai.vanteykitpvp.listeners;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AssegnoListener implements Listener {
    @SneakyThrows
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        Material mat = e.getMaterial();
        ItemStack i = e.getPlayer().getItemInHand();
        if(a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR)){
            if(mat.equals(Material.PAPER)){
                int amount = Integer.parseInt(i.getItemMeta().getDisplayName());

                Economy.add(p.getName(), amount);

                if(i.getAmount() > 1){
                    i.setAmount(i.getAmount()-1);
                }else{
                    i.setType(Material.AIR);
                }

                p.sendMessage(Format.color("&aHai riscattato l'assegno da &e" + amount));

                return;
            }

        }
    }
}
