package it.thatskai.vanteykitpvp.commands;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.manager.CoinFlipManager;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinFlipCommand implements CommandExecutor {

    private final CoinFlipManager coinflip = new CoinFlipManager();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Messages.noconsole);
            return true;
        }
        Player p = (Player) sender;

        if(args.length == 0){
            coinflip.openCoinFlipInventory(p);
        }

        if(args.length >= 1){

            if(args[0].equalsIgnoreCase("create")){
                if(args.length == 2){
                    int amount = Integer.parseInt(args[1]);

                    int min = VanteyKitPvP.getInstance().getConfig().getInt("coin-flip-min");
                    int max = VanteyKitPvP.getInstance().getConfig().getInt("coin-flip-max");

                    if(amount < min
                            || amount > max){

                        p.sendMessage(Format.color("&cPuoi scommettere solo soldi da &5" + min + " &ca &5" + max));

                        return true;
                    }


                    coinflip.createFlip(p, amount);

                    p.sendMessage(Format.color("&aHai creato con successo una scommessa da " + amount));
                }else{
                    p.sendMessage(Format.color("&c/coinflip create [quantità]"));
                }


            }
            if(args[0].equalsIgnoreCase("remove")){
                coinflip.removeFlip(p);
                p.sendMessage(Format.color("&aHai rimosso la scommessa con successo"));

            }



        }
        return true;
    }
}
