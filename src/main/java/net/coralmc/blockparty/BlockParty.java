package net.coralmc.blockparty;

import me.imbuzz.dev.gamemaker.GameMaker;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.commands.CommandManager;
import net.coralmc.blockparty.workloads.WorkloadThread;
import org.bukkit.Bukkit;

public final class BlockParty extends GameMaker {
    private WorkloadThread workloadTrhead;

    @Override
    public void onEnable() {
        super.onEnable();
        workloadTrhead = new WorkloadThread();
        minigameName = "blockparty";
        getCommand("blockparty").setExecutor(new CommandManager(this));
        Bukkit.getScheduler().runTaskTimer(this, workloadTrhead, 5L, 1L);

        getMinigameData().setStatus(GameStatus.LOBBY);
        getRedisUpdater().updateStatus(getMinigameData());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
