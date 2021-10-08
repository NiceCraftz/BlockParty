package net.coralmc.blockparty.workloads.objects;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.workloads.Workload;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TeleportablePlayer implements Workload {
    private final Player player;
    private final Location loc;

    @Override
    public void compute() {
        player.teleport(loc);
    }
}
