package net.coralmc.blockparty.listeners;

import lombok.RequiredArgsConstructor;
import me.imbuzz.dev.gamemaker.api.chat.ChatChannel;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.events.CustomDeathEvent;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.TeleportablePlayer;
import net.coralmc.blockparty.utils.ColorAPI;
import net.coralmc.blockparty.utils.ConfigurationHelper;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

import static net.coralmc.blockparty.utils.ColorAPI.*;
import static net.coralmc.blockparty.utils.ConfigurationHelper.*;

@RequiredArgsConstructor
public class GameListener implements Listener {
    private final BlockParty blockParty;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getBlockY() < getInt(blockParty, "settings.death-y")) {
            Player player = e.getPlayer();
            Optional<CoralUser> userOptional = Utils.getUser(blockParty, player.getUniqueId());
            userOptional.ifPresent(coralUser -> Bukkit.getPluginManager().callEvent(new CustomDeathEvent(player, coralUser)));

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        blockParty.getUserMap().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        blockParty.getUserMap().put(player.getUniqueId(), new CoralUser(player));
    }


    @EventHandler
    public void onCustomDeath(CustomDeathEvent e) {
        CoralUser user = e.getCoralUser();
        Player userPlayer = user.getPlayer();

        user.setChatChannel(ChatChannel.SPECTATOR);
        userPlayer.sendMessage(color(getString(blockParty, "events.died")));

        blockParty.getWorkloadTrhead().add(new TeleportablePlayer(userPlayer, getLocation(blockParty, "locations.lobby")));
        blockParty.getUserMap().remove(userPlayer.getUniqueId());

        blockParty.getUserMap()
                .values()
                .stream()
                .filter(CoralUser::isAlive)
                .forEach(coralUser -> {
                    Player coralPlayer = coralUser.getPlayer();

                    coralPlayer.sendMessage(color(getString(blockParty, "events.death-player", userPlayer.getName(), blockParty.getUserMap().size())));
                    coralPlayer.sendMessage(color(getString(blockParty, "events.point")));
                });
    }
}
