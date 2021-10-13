package net.coralmc.blockparty.utils;

import jdk.nashorn.internal.ir.Block;
import net.coralmc.blockparty.BlockParty;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.List;

public class ConfigHelper {

    public static String getString(BlockParty blockParty, String path, Object... objects) {
        return String.format(blockParty.getConfigFile().getConfiguration().getString(path), objects);
    }

    public static Location getLocation(BlockParty blockParty, String path) {
        return Utils.stringToLocation(blockParty.getLocationsFile().getConfiguration().getString(path));
    }

    public static List<String> getStringList(BlockParty blockParty, String path) {
        return blockParty.getScoreboardFile().getConfiguration().getStringList(path);
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getFormattedString(BlockParty blockParty, String path, Object... objects) {
        return color(getString(blockParty, path, objects));
    }

    public static String getScoreboardString(BlockParty blockParty, String path) {
        return color(blockParty.getScoreboardFile().getConfiguration().getString(path));
    }

    public static int getInt(BlockParty blockParty, String path) {
        return blockParty.getConfigFile().getConfiguration().getInt(path);
    }
}
