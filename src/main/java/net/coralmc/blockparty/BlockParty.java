package net.coralmc.blockparty;

import com.google.common.collect.Maps;
import lombok.Getter;
import me.imbuzz.dev.gamemaker.GameMaker;
import net.coralmc.blockparty.commands.CommandManager;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.objects.CoralFile;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.listeners.GameListener;
import net.coralmc.blockparty.objects.MapCorners;
import net.coralmc.blockparty.workloads.WorkLoadThread;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;

@Getter
public final class BlockParty extends GameMaker {

    private final Map<UUID, MapCorners> cornersMap = Maps.newHashMap();

    private CoralFile configFile;
    private CoralFile locationsFile;
    private CoralFile blocksFile;
    private CoralFile scoreboardFile;

    private BlockPartyGame game;
    private WorkLoadThread workLoadThread;

    @Override
    public void onEnable() {
        super.onEnable();
        minigameName = "blockparty";

        configFile = new CoralFile(this, "config.yml");
        locationsFile = new CoralFile(this, "locations.yml");
        blocksFile = new CoralFile(this, "blocks.yml");
        scoreboardFile = new CoralFile(this, "scoreboards.yml");

        game = new BlockPartyGame(this);
        workLoadThread = new WorkLoadThread();

        getCommand("blockparty").setExecutor(new CommandManager(this));
        Bukkit.getScheduler().runTaskTimer(this, workLoadThread, 5L, 1L);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);

        game.setStatus(BlockPartyStatus.STARTING);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
