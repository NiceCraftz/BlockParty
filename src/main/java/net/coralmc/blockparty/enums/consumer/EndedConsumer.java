package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.game.BlockPartyGame;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class EndedConsumer implements Consumer<BlockPartyGame> {
    @Override
    public void accept(BlockPartyGame blockPartyGame) {

        Bukkit.broadcastMessage()

    }
}
