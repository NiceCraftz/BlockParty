package net.coralmc.blockparty.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.enums.consumer.CooldownConsumer;
import net.coralmc.blockparty.enums.consumer.EndedConsumer;
import net.coralmc.blockparty.enums.consumer.StartingConsumer;
import net.coralmc.blockparty.game.BlockPartyGame;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Getter
public enum BlockPartyStatus {

    STARTING("&aAttesa Giocatori", consumer -> {}),
    LOBBY("&2Start Timer", new CooldownConsumer()),
    PLAYING("&eIniziato", new StartingConsumer()),
    END("&cFinito", new EndedConsumer());

    private final String name;
    private final Consumer<BlockPartyGame> consumer;
}
