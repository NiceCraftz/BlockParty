package net.coralmc.blockparty;

import com.google.common.collect.Lists;
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
import org.bukkit.Location;

import java.util.List;

import static net.coralmc.blockparty.utils.ConfigurationHelper.getLocation;
import static net.coralmc.blockparty.utils.Utils.loadLocations;

@Getter
public final class BlockParty extends GameMaker {
    private final List<Location> locationList = Lists.newArrayList();

    private BlockPartyGame game;
    private WorkLoadThread workLoadThread;

    private CoralFile configFile;
    private CoralFile locationsFile;

    @Override
    public void onEnable() {
        super.onEnable();
        game = new BlockPartyGame(this, new BlockPartyData(getMinigameData(), getRedisUpdater()));

        configFile = new CoralFile(this, "config.yml");
        locationsFile = new CoralFile(this, "locations.yml");

        workLoadThread = new WorkLoadThread();
        minigameName = "blockparty";

        locationList.addAll(loadLocations(
                getLocation(this, "corner-a"),
                getLocation(this, "corner-b")
        ));

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
