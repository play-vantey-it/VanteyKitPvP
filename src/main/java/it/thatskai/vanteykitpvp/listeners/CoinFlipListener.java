package it.thatskai.vanteykitpvp.listeners;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.manager.CoinFlipManager;
import it.thatskai.vanteykitpvp.utils.Format;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class CoinFlipListener implements Listener {

    private final CoinFlipManager coinflip = new CoinFlipManager();
    @SneakyThrows
    @EventHandler
    public void onGame(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if(e.getView().getTitle().equals(Format.color("&6CoinFlip"))){
            e.setCancelled(true);
            String player_name = e.getCurrentItem().getItemMeta().getDisplayName();
            Player p2 = Bukkit.getPlayer(player_name);
            int amount = coinflip.getAmount(p2);

            double money = Economy.getMoney(p.getName());

            if(money < amount){
                p.sendMessage(Format.color("&cNon hai abbastanza soldi!"));
                return;
            }

            if(p == p2){
                p.sendMessage(Format.color("&cNon puoi scommettere da solo!"));
                return;
            }

            p.closeInventory();

            // TODO: fare quel fottutissimo sistema del 50% intanto testo senza! :D

            p2.sendMessage("Hai vinto!");

            p.sendMessage("Hai perso!");

            Economy.add(p2.getName(), amount*2);

            Economy.subtract(p.getName(), amount);

            coinflip.terminateFlip(p2);


        }
    }
}
