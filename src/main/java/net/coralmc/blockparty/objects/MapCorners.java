package net.coralmc.blockparty.objects;

import lombok.Data;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.utils.ConfigHelper;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

@Data
public class MapCorners {
    private Location firstLocation;
    private Location secondLocation;

    public void apply(BlockParty blockParty, CommandSender commandSender) {
        if (firstLocation == null
                || secondLocation == null
                || !firstLocation.getWorld().equals(secondLocation.getWorld())) {

            commandSender.sendMessage(ConfigHelper.getFormattedString(blockParty, "locations-invalid"));
            return;
        }

        blockParty.getGame().setLocationList(Utils.loadLocations(firstLocation, secondLocation));

        FileConfiguration config = blockParty.getLocationsFile().getConfiguration();
        config.set("corner-a", Utils.locationToString(firstLocation));
        config.set("corner-b", Utils.locationToString(secondLocation));
        blockParty.getLocationsFile().save();
        blockParty.getLocationsFile().reload();
    }
}
