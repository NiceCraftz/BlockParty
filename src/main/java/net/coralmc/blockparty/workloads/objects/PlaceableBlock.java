package net.coralmc.blockparty.workloads.objects;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.objects.CustomBlock;
import net.coralmc.blockparty.workloads.Workload;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class PlaceableBlock implements Workload {
    private final Location location;
    private final List<CustomBlock> blockList;

    @Override
    public void compute() {
        int rand = ThreadLocalRandom.current().nextInt(blockList.size());
        final CustomBlock cb = blockList.get(rand);
        location.getBlock().setType(cb.getMat());
        location.getBlock().setData(cb.getData());
    }
}
