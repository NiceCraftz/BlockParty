package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.objects.TeleportablePlayer;
import net.coralmc.blockparty.utils.ConfigurationHelper;

import java.util.function.Consumer;

public class StartingConsumer implements Consumer<BlockPartyGame> {
    @Override
    public void accept(BlockPartyGame blockPartyGame) {


        blockPartyGame.getUserMap().values().forEach(coralUser ->
                blockPartyGame.getBlockParty()
                        .getWorkLoadThread()
                        .add(new TeleportablePlayer(coralUser.getPlayer(),
                                ConfigurationHelper.getLocation(blockPartyGame.getBlockParty(),
                                        "spawn"))));

        blockPartyGame.startGame();

    }
}
