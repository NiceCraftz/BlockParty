package net.coralmc.blockparty.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class CoralLocations {
    private Location firstLocation;
    private Location secondLocation;
}
