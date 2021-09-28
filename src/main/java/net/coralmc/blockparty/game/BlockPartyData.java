package net.coralmc.blockparty.game;

import lombok.Getter;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import me.imbuzz.dev.gamemaker.communications.MinigameData;
import me.imbuzz.dev.gamemaker.communications.RedisUpdater;
import net.coralmc.blockparty.enums.BlockPartyStatus;

@Getter
public class BlockPartyData {
    private final MinigameData minigameData;
    private final RedisUpdater redisUpdater;
    private BlockPartyStatus status;

    public BlockPartyData(MinigameData minigameData, RedisUpdater redisUpdater) {
        this.minigameData = minigameData;
        this.redisUpdater = redisUpdater;
    }

    public void setStatus(BlockPartyStatus status) {
        if (this.status == status) return;

        this.status = status;
        minigameData.setStatus(GameStatus.valueOf(status.toString()));
        redisUpdater.updateStatus(minigameData);
    }

}
