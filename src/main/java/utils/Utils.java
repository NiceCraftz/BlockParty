package utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Utils {
    public static String locationToString(Location location) {
        return location.getWorld().getName() + ";"
                + location.getBlockX() + ";"
                + location.getBlockY() + ";"
                + location.getBlockZ();
    }

    public static Location stringToLocation(String string) {
        String[] s = string.split(";");
        return new Location(Bukkit.getWorld(s[0]),
                Double.parseDouble(s[1]),
                Double.parseDouble(s[2]),
                Double.parseDouble(s[3]));
    }
}
