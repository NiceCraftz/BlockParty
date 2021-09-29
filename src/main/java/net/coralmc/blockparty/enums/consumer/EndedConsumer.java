package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.TeleportablePlayer;
import net.coralmc.blockparty.utils.ConfigurationHelper;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Consumer;

import static net.coralmc.blockparty.utils.ConfigurationHelper.*;

public class EndedConsumer implements Consumer<BlockPartyGame> {
    @Override
    public void accept(BlockPartyGame blockPartyGame) {
        BlockParty blockParty = blockPartyGame.getBlockParty();

        Bukkit.broadcastMessage("");

        if (blockPartyGame.getUserMap().size() >= 1) {
            UUID key = UUID.fromString(blockPartyGame.getUserMap().keySet().toArray()[0].toString());
            CoralUser coralUser = blockPartyGame.getUserMap().get(key);
            Bukkit.broadcastMessage(getFormattedString(
                    blockParty, "winner-announce", coralUser.getPlayer().getName(), coralUser.getCoins()
            ));
        }

        Bukkit.getOnlinePlayers().forEach(player -> blockParty.getWorkLoadThread().add(
                new TeleportablePlayer(player, getLocation(blockParty, "lobby"))
        ));

        Bukkit.getScheduler().runTaskLater(blockParty, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.kickPlayer(getFormattedString(blockParty, "kick-message"));
            });

            Bukkit.shutdown();
        }, 20L * getInt(blockParty, "kick-time"));


    }
}
