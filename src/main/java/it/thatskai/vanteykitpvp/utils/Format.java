package it.thatskai.vanteykitpvp.utils;

import org.bukkit.ChatColor;

public class Format {

    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
