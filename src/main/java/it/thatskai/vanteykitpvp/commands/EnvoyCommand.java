package it.thatskai.vanteykitpvp.commands;

import it.thatskai.vanteykitpvp.manager.EnvoyManager;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnvoyCommand implements CommandExecutor {
    private final EnvoyManager envoy = new EnvoyManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.noconsole);
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("kitpvp.envoy")){
            p.sendMessage(Format.color(Messages.noperms));
            return true;
        }

        if(args.length == 0){
            p.sendMessage(Format.color("&c/envoy start"));
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("start")){
                envoy.startEnvoy(p.getLocation());
            }else{
                p.sendMessage(Format.color("&cInvalid sub-command"));
            }
        }


        return true;
    }
}
