package it.thatskai.vanteykitpvp.commands;

import it.thatskai.vanteykitpvp.manager.KothManager;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KothCommand implements CommandExecutor {
    private final KothManager koth = new KothManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.noconsole);
            return true;
        }
        Player p = (Player) sender;

        if(!p.hasPermission("kitpvp")){
            p.sendMessage(Format.color(Messages.noperms));
            return true;
        }
        if(args.length == 0){
            p.sendMessage(Format.color("&c/koth start - Starta l'evento KOTH\n&c/koth stop - Ferma l'evento KOTH"));
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("start")){
                p.sendMessage(Format.color("&aHai startato l'evento KOTH!"));
                koth.startKoth();
            }
            if(args[0].equalsIgnoreCase("stop")){
                p.sendMessage(Format.color("&cHai stoppato l'evento KOTH"));
                koth.forceStopKoth();
            }

        }

        return true;
    }
}
