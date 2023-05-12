package it.thatskai.vanteykitpvp.commands;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            if(sender.hasPermission("kitpvp.admin")){
                sender.sendMessage(Format.color("&bRunning &5VanteyKitPvP&b by ThatsKai_\n&f- /vkitpvp reload"));
            }else{
                sender.sendMessage(Format.color("&bRunning &5VanteyKitPvP&b by ThatsKai_"));
            }
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                if(!sender.hasPermission("kitpvp.admin")){
                    sender.sendMessage(Format.color(Messages.noperms));
                    return true;
                }
                VanteyKitPvP.getInstance().reloadConfig();
                VanteyKitPvP.getInstance().saveConfig();
                sender.sendMessage("Configurazione ricaricata!");
            }else{
                sender.sendMessage("Invalid sub-command");
            }
        }
        return true;
    }
}
