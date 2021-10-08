package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.objects.CoralUser;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

public class EndedConsumer implements Consumer<BlockPartyGame> {

    @Override
    public void accept(BlockPartyGame blockPartyGame) {
        BlockParty blockParty = blockPartyGame.getBlockParty();

        if (blockPartyGame.getUserMap().size() >= 1) {
            Optional<Map.Entry<UUID, CoralUser>> key = blockPartyGame.getUserMap()
                    .entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue().isSpectator()).findFirst();

            if (!key.isPresent()) return;

            CoralUser coralUser = blockPartyGame.getUserMap().get(key.get().getKey());

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(getFormattedString(
                    blockParty, "winner-announce", coralUser.getPlayer().getName(), coralUser.getCoins()
            ));
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer(getFormattedString(blockParty, "kick-message"));
        });

        Bukkit.shutdown();
    }
}
