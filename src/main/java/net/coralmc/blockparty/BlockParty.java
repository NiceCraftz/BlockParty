package net.coralmc.blockparty;

import lombok.Getter;
import me.imbuzz.dev.gamemaker.GameMaker;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.commands.CommandManager;
import net.coralmc.blockparty.files.CoralFile;
import net.coralmc.blockparty.game.BlockPartyData;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.listeners.GameListener;
import net.coralmc.blockparty.workloads.WorkLoadThread;
import org.bukkit.Bukkit;

@Getter
public final class BlockParty extends GameMaker {
    private BlockPartyGame game;
    private WorkLoadThread workLoadThread;

    private CoralFile configFile;
    private CoralFile locationsFile;
    private CoralFile blocksFile;

    @Override
    public void onEnable() {
        super.onEnable();
        game = new BlockPartyGame(this, new BlockPartyData(this));

        configFile = new CoralFile(this, "config.yml");
        locationsFile = new CoralFile(this, "locations.yml");
        blocksFile = new CoralFile(this, "blocks.yml");

        workLoadThread = new WorkLoadThread();
        minigameName = "blockparty";

        getCommand("blockparty").setExecutor(new CommandManager(this));
        Bukkit.getScheduler().runTaskTimer(this, workLoadThread, 5L, 1L);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        getMinigameData().setStatus(GameStatus.LOBBY);
        getRedisUpdater().updateStatus(getMinigameData());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
