package net.coralmc.blockparty.workloads;

import com.google.common.collect.Queues;
import org.bukkit.Bukkit;

import java.util.ArrayDeque;
import java.util.Objects;

public class WorkLoadThread implements Runnable {
    private final ArrayDeque<Workload> workloads;
    private static final int MAX_MS_PER_TICK = 10;

    public void add(Workload workload) {
        workloads.add(workload);
    }

    public WorkLoadThread() {
        workloads = Queues.newArrayDeque();
    }

    @Override
    public void run() {
        long stopTime = System.currentTimeMillis() + MAX_MS_PER_TICK;
        while(!workloads.isEmpty() && System.currentTimeMillis() <= stopTime) {
            Objects.requireNonNull(workloads.poll()).compute();
        }
    }
}
