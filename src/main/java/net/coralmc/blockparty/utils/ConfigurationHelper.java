package net.coralmc.blockparty.utils;

import net.coralmc.blockparty.BlockParty;

import java.util.List;

public class ConfigurationHelper {
    public static String getString(BlockParty blockParty, String path) {
        return blockParty.getConfig().getConfiguration().getString(path);
    }

    public static String getString(BlockParty blockParty, String path, Object... objects) {
        return String.format(blockParty.getConfig().getConfiguration().getString(path), objects);
    }

    public static List<String> getStringList(BlockParty blockParty, String path) {
        return blockParty.getConfig().getConfiguration().getStringList(path);
    }

    public static int getInt(BlockParty blockParty, String path) {
        return blockParty.getConfig().getConfiguration().getInt(path);
    }
}
