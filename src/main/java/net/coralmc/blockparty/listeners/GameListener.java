package net.coralmc.blockparty.listeners;

import lombok.RequiredArgsConstructor;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import me.imbuzz.dev.gamemaker.api.chat.ChatChannel;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.events.CustomDeathEvent;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.TeleportablePlayer;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

import static net.coralmc.blockparty.utils.ConfigurationHelper.*;

public class GameListener implements Listener {
    private final BlockParty blockParty;
    private final BlockPartyGame game;

    public GameListener(BlockParty blockParty) {
        this.blockParty = blockParty;
        game = blockParty.getGame();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Optional<CoralUser> userOptional = Utils.getUser(blockParty, player.getUniqueId());

        if (userOptional.isPresent() && e.getTo().getBlockY() <= getInt(blockParty, "settings.death-y")) {
            Bukkit.getPluginManager().callEvent(new CustomDeathEvent(player, userOptional.get()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Optional<CoralUser> userOptional = Utils.getUser(blockParty, player.getUniqueId());
        if (!userOptional.isPresent()) return;

        Utils.announceDeath(blockParty, player);
        game.getUserMap().remove(player.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        GameStatus status = blockParty.getMinigameData().getStatus();

        CoralUser coralUser = new CoralUser(player);
        game.getUserMap().put(player.getUniqueId(), coralUser);

        if (status == GameStatus.PLAYING || status == GameStatus.END) {
            player.setGameMode(GameMode.SPECTATOR);
            coralUser.setChatChannel(ChatChannel.SPECTATOR);

            blockParty.getWorkLoadThread().add(new TeleportablePlayer(player,
                    getLocation(blockParty, "spawn"))
            );

            return;
        }

        blockParty.getWorkLoadThread().add(new TeleportablePlayer(player,
                getLocation(blockParty, "lobby"))
        );
    }

    @EventHandler
    public void onCustomDeath(CustomDeathEvent e) {
        CoralUser user = e.getCoralUser();
        Player userPlayer = e.getPlayer();

        user.setChatChannel(ChatChannel.SPECTATOR);
        userPlayer.sendMessage(getFormattedString(blockParty, "died"));

        blockParty.getWorkLoadThread().add(new TeleportablePlayer(userPlayer,
                getLocation(blockParty, "lobby"))
        );

        game.getUserMap().remove(userPlayer.getUniqueId());
        Utils.announceDeath(blockParty, userPlayer);
    }
}
