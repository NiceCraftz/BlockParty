package net.coralmc.blockparty.game;

import lombok.Getter;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;

@Getter
public class BlockPartyData {
    private final BlockParty blockParty;
    private BlockPartyStatus status;

    public BlockPartyData(BlockParty blockParty) {
        this.blockParty = blockParty;
    }

    public void setStatus(BlockPartyStatus status) {
        if (this.status == status) return;
        this.status = status;

        status.getConsumer().accept(blockParty.getGame());
        blockParty.getMinigameData().setStatus(GameStatus.valueOf(status.toString()));
        blockParty.getRedisUpdater().updateStatus(blockParty.getMinigameData());
    }

}
