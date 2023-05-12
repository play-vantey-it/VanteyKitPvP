package it.thatskai.vanteykitpvp.commands;

import com.earth2me.essentials.api.Economy;
import it.thatskai.vanteykitpvp.VanteyKitPvP;
import it.thatskai.vanteykitpvp.manager.CoinFlipManager;
import it.thatskai.vanteykitpvp.utils.Format;
import it.thatskai.vanteykitpvp.utils.Messages;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinFlipCommand implements CommandExecutor {

    private final CoinFlipManager coinflip = new CoinFlipManager();
    @SneakyThrows
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

                    double money = Economy.getMoney(p.getName());

                    if(amount < min
                            || amount > max){

                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("invalid-money")
                                .replace("%min%", String.valueOf(min))
                                .replace("%max%", String.valueOf(max))));

                        return true;
                    }

                    if(money < amount){
                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("not-enough-money")
                                .replace("%min%", String.valueOf(min))
                                .replace("%max%", String.valueOf(max))));
                        return true;
                    }

                    if(CoinFlipManager.getCoinFlip().getInt("players-list."+p.getName()+".coin") != 0){
                        p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("already-has-a-coinflip")));
                        return true;
                    }

                    coinflip.createFlip(p, amount);

                    p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("coinflip-created")
                            .replace("%amount%", String.valueOf(amount))));
                }else{
                    p.sendMessage(Format.color("&c/coinflip create [quantità]"));
                }


            }
            if(args[0].equalsIgnoreCase("remove")){
                coinflip.removeFlip(p);
                p.sendMessage(Format.color(VanteyKitPvP.getInstance().getConfig().getString("coinflip-removed")));

            }
        }
        return true;
    }
}
