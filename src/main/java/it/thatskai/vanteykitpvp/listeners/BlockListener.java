package it.thatskai.vanteykitpvp.listeners;

import it.thatskai.vanteykitpvp.manager.KothManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    private final KothManager koth = new KothManager();
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();

        Location loc = e.getBlockPlaced().getLocation();
        Block block = e.getBlockAgainst();
        Block solidBlock = findSolidBlock(loc);


        if(koth.getState()){
            if(block.getType().equals(Material.WOOL) || solidBlock.getType().equals(Material.WOOL)){
                byte data = block.getData();
                byte data2 = solidBlock.getData();

                if(data == 14 || data == 0 || data2 == 14 || data2 == 0){
                    e.setCancelled(true);
                }

            }
        }
    }

    private Block findSolidBlock(Location location) {
        Block block = location.getBlock();

        while (block.getType() == Material.AIR && block.getY() > 0) {
            block = block.getLocation().subtract(0, 1, 0).getBlock();
        }

        return block.getType() != Material.AIR ? block : null;
    }
}
