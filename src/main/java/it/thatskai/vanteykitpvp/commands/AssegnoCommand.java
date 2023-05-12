package it.thatskai.vanteykitpvp.commands;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
            p.sendMessage("/assegno [quantità]");
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("give")){

                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                p.sendMessage("/assegno [quantità | give | help]");
                return true;
            }

            int amount = Integer.parseInt(args[0]);

            if(amount > Economy.getMoney(p.getName())){
                p.sendMessage(Format.color("&cNon hai abbastanza soldi per creare questo assegno!"));
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

            p.sendMessage(Format.color("&aHai creato correttamente un assegno da &e" + amount));


        }





        return true;
    }
}
