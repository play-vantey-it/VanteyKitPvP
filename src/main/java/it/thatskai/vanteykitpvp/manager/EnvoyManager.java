package it.thatskai.vanteykitpvp.manager;

import it.thatskai.vanteykitpvp.VanteyKitPvP;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnvoyManager {
    private static List<Material> materials = new ArrayList<>();
    /*public void startEnvoy(Location centerLocation) {
        for (int i = 0; i < 10; i++) {
            Location randomLocation = getRandomLocation(centerLocation, 50);
            Block block = randomLocation.getBlock();

            // Trova il blocco fisico sotto la posizione randomLocation
            while (block.getType() == Material.AIR || block.getType().isTransparent()) {
                randomLocation.subtract(0, 1, 0);
                block = randomLocation.getBlock();
            }

            // Controllo se il blocco trovato è sostituibile
            if (block.getType().isSolid()) {
                Location chestLocation = randomLocation.clone().add(0, 1, 0);
                block.setType(Material.CHEST);

                // Crea una variabile per la cassa creata
                Chest chest = chestLocation.getBlock().getType().equals(Material.CHEST);
                Inventory chestInventory = chest.getInventory();

                for(String str : VanteyKitPvP.envoy.getStringList("chest-items")){
                    Material m = Material.getMaterial(str);
                    ItemStack mat = new ItemStack(m);
                    chestInventory.addItem(mat);
                }

            }
        }
    }

    public Location getRandomLocation(Location centerLocation, int radius) {
        int offsetX = (int) (Math.random() * radius * 2 - radius);
        int offsetZ = (int) (Math.random() * radius * 2 - radius);
        int offsetY = 0; // Altezza desiderata

        Location randomLocation = centerLocation.clone().add(offsetX, offsetY, offsetZ);
        return randomLocation;
    }*/

}
