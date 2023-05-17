package it.thatskai.vanteykitpvp.commands;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AssegnoCommand implements CommandExecutor {
    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.noconsole);
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0){
            List<String> msgs = VanteyKitPvP.getInstance().getConfig().getStringList("assegno-help");

            for(String message : msgs){
                p.sendMessage(Format.color(message));
            }
        }

        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("give")){
                if(!p.hasPermission("kitpvp.give")){
                    p.sendMessage(Format.color(Messages.noperms));
                    return true;
                }

                if(args.length == 3){
                    Player t = Bukkit.getPlayer(args[1]);
                    String amount = args[2];

                    //Crea l'assegno
                    ItemStack assegno = new ItemStack(Material.PAPER);
                    ItemMeta assegno_meta = assegno.getItemMeta();
                    assegno_meta.setDisplayName(amount + "$");
                    assegno.setItemMeta(assegno_meta);

                    //Da l'assegno al giocatore
                    t.getInventory().addItem(assegno);

                    p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("assegno-give-sender")
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%player%", t.getName())));

                    t.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("assegno-give-sender")
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%player%", p.getName())));


                }else{
                    p.sendMessage(Format.color("&c/assegno give [player] [quantità]"));
                }



                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                List<String> msgs = VanteyKitPvP.getInstance().getConfig().getStringList("assegno-help");

                for(String message : msgs){
                    p.sendMessage(Format.color(message));
                }
                return true;
            }

            int amount = Integer.parseInt(args[0]);

            if(amount > Economy.getMoney(p.getName())){
                p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("not-enough-money")));
                return true;
            }

            //Toglie soldi al giocatore
            Economy.subtract(p.getName(), amount);


            //Crea l'assegno
            ItemStack assegno = new ItemStack(Material.PAPER);
            ItemMeta assegno_meta = assegno.getItemMeta();
            assegno_meta.setDisplayName(String.valueOf(amount) + "$");
            assegno.setItemMeta(assegno_meta);

            //Da l'assegno al giocatore
            p.getInventory().addItem(assegno);

            p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("assegno-creato")
                    .replace("%amount%", String.valueOf(amount))));
        }
        return true;
    }
}
