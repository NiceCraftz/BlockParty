package net.coralmc.blockparty.game;

import com.google.common.collect.Maps;
import lombok.Data;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.enums.GameDifficulty;
import net.coralmc.blockparty.game.task.GameTask;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.CustomBlock;
import net.coralmc.blockparty.utils.GameUtils;
import net.coralmc.blockparty.workloads.objects.PlaceableBlock;
import net.coralmc.blockparty.workloads.objects.RemovableBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static net.coralmc.blockparty.utils.ConfigHelper.*;
import static net.coralmc.blockparty.utils.Utils.loadLocations;

@Data
public class BlockPartyGame {

    private GameDifficulty gameDifficulty = GameDifficulty.EASY;
    private final Map<UUID, CoralUser> userMap = Maps.newHashMap();
    private final BlockParty blockParty;

    private List<CustomBlock> blockList;
    private List<Location> locationList;

    private BlockPartyStatus status;
    private GameTask gameTask;
    private CustomBlock customBlock;

    public BlockPartyGame(BlockParty blockParty) {
        this.blockParty = blockParty;
        setStatus(BlockPartyStatus.STARTING);
        init();
    }

    public void setStatus(BlockPartyStatus status) {
        if (this.status == status) return;
        this.status = status;

        status.getConsumer().accept(this);

        blockParty.getMinigameData().setStatus(GameStatus.valueOf(status.toString()));
        blockParty.getRedisUpdater().updateStatus(blockParty.getMinigameData());
    }

    private void init() {
        locationList = loadLocations(getLocation(blockParty, "corner-a"), getLocation(blockParty, "corner-b"));
        blockList = GameUtils.loadBlocks(blockParty);
        customBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));
    }

    public void startGame() {
        refill();
        gameTask = new GameTask(this);
        gameTask.runTaskTimer(blockParty, 0, 20);

        userMap.values().forEach(coralUser -> GameUtils.setGameBoard(blockParty, coralUser));
    }

    public void blockTime() {
        CustomBlock newBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));

        while (customBlock.equals(newBlock)) {
            newBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));
        }

        customBlock = newBlock;

        Bukkit.broadcastMessage(getFormattedString(blockParty, "block-announcement", customBlock.getName()));
        userMap.values().forEach(coralUser -> coralUser.getPlayer().getInventory().setItem(4, customBlock.getItem()));
    }

    public void delete() {
        locationList.forEach(location -> blockParty.getWorkLoadThread().add(new RemovableBlock(location, customBlock)));
        Bukkit.getScheduler().runTaskLater(blockParty, this::refill, 20L * getInt(blockParty, "refill-time"));
    }

    public void refill() {
        locationList.forEach(location -> blockParty.getWorkLoadThread().add(new PlaceableBlock(location, blockList)));
        userMap.values().forEach(coralUser -> coralUser.getPlayer().getInventory().clear());
    }


}
