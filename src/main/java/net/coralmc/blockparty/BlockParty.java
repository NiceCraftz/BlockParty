package net.coralmc.blockparty;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.imbuzz.dev.gamemaker.GameMaker;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.commands.CommandManager;
import net.coralmc.blockparty.files.CoralFile;
import net.coralmc.blockparty.listeners.GameListener;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.workloads.WorkloadThread;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

@Getter
public final class BlockParty extends GameMaker {
    private final Map<UUID, CoralUser> userMap = Maps.newHashMap();
    private WorkloadThread workloadTrhead;
    private CoralFile config;

    @Override
    public void onEnable() {
        super.onEnable();
        config = new CoralFile(this, "config.yml");
        workloadTrhead = new WorkloadThread();
        minigameName = "blockparty";


        getCommand("blockparty").setExecutor(new CommandManager(this));
        Bukkit.getScheduler().runTaskTimer(this, workloadTrhead, 5L, 1L);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getMinigameData().setStatus(GameStatus.LOBBY);
        getRedisUpdater().updateStatus(getMinigameData());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public String getString(String path) {
        return config.getConfiguration().getString(path);
    }
}
