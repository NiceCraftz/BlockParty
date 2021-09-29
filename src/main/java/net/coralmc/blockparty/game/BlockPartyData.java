package net.coralmc.blockparty.game;

import lombok.Getter;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import me.imbuzz.dev.gamemaker.communications.MinigameData;
import me.imbuzz.dev.gamemaker.communications.RedisUpdater;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;

@Getter
public class BlockPartyData {
    private final MinigameData minigameData;
    private final RedisUpdater redisUpdater;
    private final BlockPartyGame blockPartyGame;
    private BlockPartyStatus status;

    public BlockPartyData(BlockParty blockParty) {
        minigameData = blockParty.getMinigameData();
        redisUpdater = blockParty.getRedisUpdater();
        blockPartyGame = blockParty.getGame();
    }

    public void setStatus(BlockPartyStatus status) {
        if (this.status == status) return;
        this.status = status;

        status.getConsumer().accept(blockPartyGame);
        minigameData.setStatus(GameStatus.valueOf(status.toString()));
        redisUpdater.updateStatus(minigameData);
    }

}
