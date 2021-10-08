package net.coralmc.blockparty.utils;

import net.coralmc.blockparty.BlockParty;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class ConfigHelper {

    public static String getString(BlockParty blockParty, String path, Object... objects) {
        return String.format(blockParty.getConfigFile().getConfiguration().getString(path), objects);
    }

    public static Location getLocation(BlockParty blockParty, String path) {
        return Utils.stringToLocation(blockParty.getLocationsFile().getConfiguration().getString(path));
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getFormattedString(BlockParty blockParty, String path, Object... objects) {
        return color(getString(blockParty, path, objects));
    }

    public static int getInt(BlockParty blockParty, String path) {
        return blockParty.getConfigFile().getConfiguration().getInt(path);
    }
}
