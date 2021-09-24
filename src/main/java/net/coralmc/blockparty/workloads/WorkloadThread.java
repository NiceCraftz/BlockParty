package net.coralmc.blockparty.workloads;

import com.google.common.collect.Queues;

import java.util.ArrayDeque;
import java.util.Objects;

public class WorkloadThread implements Runnable {
    private final ArrayDeque<Workload> workloads;
    private static final int MAX_MS_PER_TICK = 10;

    public WorkloadThread() {
        workloads = Queues.newArrayDeque();
    }

    @Override
    public void run() {
        long stopTime = System.currentTimeMillis() + MAX_MS_PER_TICK;
        while(!workloads.isEmpty() && stopTime <= System.currentTimeMillis()) {
            Objects.requireNonNull(workloads.poll()).compute();
        }
    }
}
