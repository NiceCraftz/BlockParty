package net.coralmc.blockparty.objects;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.workloads.Workload;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class PlaceableBlock implements Workload {
    private final Location loc;
    private final List<CustomBlock> materialList;

    @Override
    public void compute() {
        int rand = ThreadLocalRandom.current().nextInt(materialList.size());
        final CustomBlock cb = materialList.get(rand);
        loc.getBlock().setType(cb.getMat());
        loc.getBlock().setData(cb.getData());
    }
}
