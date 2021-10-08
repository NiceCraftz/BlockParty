package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.utils.ConfigHelper;
import net.coralmc.blockparty.workloads.objects.TeleportablePlayer;

import java.util.function.Consumer;

public class StartingConsumer implements Consumer<BlockPartyGame> {
    @Override
    public void accept(BlockPartyGame blockPartyGame) {
        blockPartyGame.getUserMap().values().forEach(coralUser ->
                blockPartyGame.getBlockParty()
                        .getWorkLoadThread()
                        .add(new TeleportablePlayer(coralUser.getPlayer(),
                                ConfigHelper.getLocation(blockPartyGame.getBlockParty(),
                                        "spawn").add(0.5, 0, 0.5))));
        blockPartyGame.startGame();

    }
}
