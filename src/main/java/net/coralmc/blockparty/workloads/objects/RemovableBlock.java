package net.coralmc.blockparty.workloads.objects;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.objects.CustomBlock;
import net.coralmc.blockparty.workloads.Workload;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public class RemovableBlock implements Workload {
    private final Location location;
    private final CustomBlock customBlock;

    @Override
    public void compute() {
        Block block = location.getBlock();
        if (block.getType() != customBlock.getMat() || block.getData() != customBlock.getData()) {
            block.setType(Material.AIR);
        }
    }
}
