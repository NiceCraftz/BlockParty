package net.coralmc.blockparty.game;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.enums.GameDifficulty;
import net.coralmc.blockparty.game.task.GameTask;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.CustomBlock;
import net.coralmc.blockparty.objects.PlaceableBlock;
import net.coralmc.blockparty.objects.RemovableBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static net.coralmc.blockparty.utils.ConfigurationHelper.*;
import static net.coralmc.blockparty.utils.Utils.loadLocations;

@Data
public class BlockPartyGame {

    private final Map<UUID, CoralUser> userMap = Maps.newHashMap();
    private List<CustomBlock> blockList;
    private List<Location> locationList;

    private final BlockPartyData data;
    private final BlockParty blockParty;

    private GameDifficulty gameDifficulty = GameDifficulty.EASY;
    private CustomBlock customBlock;
    private GameTask gameTask;

    public BlockPartyGame(BlockParty blockParty, BlockPartyData data) {
        this.blockParty = blockParty;
        this.data = data;
        data.setStatus(BlockPartyStatus.LOBBY);

        init();
    }

    private void init() {
        locationList = loadLocations(getLocation(blockParty, "corner-a"), getLocation(blockParty, "corner-b"));
        blockList = GameUtils.loadBlocks(blockParty);
    }


    public void startGame() {
        refill();
        gameTask = new GameTask(this);
        gameTask.runTaskTimer(blockParty, 0, 20);
    }

    public void blockTime() {
        CustomBlock newBlock;
        do {
            newBlock = blockList.get(ThreadLocalRandom.current().nextInt(blockList.size()));
        } while (customBlock.equals(newBlock));

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
