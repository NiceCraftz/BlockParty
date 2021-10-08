package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.game.task.CountdownTask;

import java.util.function.Consumer;

public class CooldownConsumer implements Consumer<BlockPartyGame> {

    @Override
    public void accept(BlockPartyGame blockPartyGame) {
        new CountdownTask(blockPartyGame).runTaskTimerAsynchronously(blockPartyGame.getBlockParty(), 0, 20L);
    }
}
