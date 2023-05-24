package it.thatskai.vanteykitpvp.commands;

import it.thatskai.vanteykitpvp.manager.ShopManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    ShopManager shop = new ShopManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Solo i giocatori possono eseguire questo comando");
            return true;
        }
        Player p = (Player) sender;
        shop.openMainShop(p);
        return true;
    }
}
