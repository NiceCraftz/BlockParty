package utils;

import net.coralmc.blockparty.BlockParty;
import org.bukkit.ChatColor;

public class ColorAPI {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String color(BlockParty blockParty, String path) {
        return color(blockParty.getConfig().getString(path));
    }
}
