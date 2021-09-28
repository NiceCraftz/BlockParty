package net.coralmc.blockparty.utils;

import org.bukkit.ChatColor;

public class ColorAPI {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
