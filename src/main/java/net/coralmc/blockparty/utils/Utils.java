package net.coralmc.blockparty.utils;

import com.google.common.collect.Lists;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.objects.CoralUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Utils {
    public static String locationToString(Location location) {
        return location.getWorld().getName() + ";"
                + location.getBlockX() + ";"
                + location.getBlockY() + ";"
                + location.getBlockZ() + ";"
                + location.getYaw() + ";"
                + location.getPitch();
    }

    public static Location stringToLocation(String string) {
        String[] s = string.split(";");
        return new Location(Bukkit.getWorld(s[0]),
                Double.parseDouble(s[1]),
                Double.parseDouble(s[2]),
                Double.parseDouble(s[3]),
                Float.parseFloat(s[4]),
                Float.parseFloat(s[5]));

    }

    public static Optional<CoralUser> getUser(BlockParty blockParty, UUID uuid) {
        return Optional.ofNullable(blockParty.getGame().getUserMap().get(uuid));
    }

    public static ItemStack getAxe(BlockParty blockParty) {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ConfigHelper.getFormattedString(blockParty, "axe-name"));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static List<Location> loadLocations(Location loc1, Location loc2) {
        List<Location> locationList = Lists.newArrayList();
        if (!loc1.getWorld().getUID().equals(loc2.getWorld().getUID())) return locationList;

        for (int x = Integer.min(loc1.getBlockX(), loc2.getBlockX()); x <= Integer.max(loc1.getBlockX(), loc2.getBlockX()); x++) {
            for (int y = Integer.min(loc1.getBlockY(), loc2.getBlockY()); y <= Integer.max(loc1.getBlockY(), loc2.getBlockY()); y++) {
                for (int z = Integer.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Integer.max(loc1.getBlockZ(), loc2.getBlockZ()); z++) {
                    locationList.add(new Location(loc1.getWorld(), x, y, z));
                }
            }
        }
        return locationList;
    }
}
