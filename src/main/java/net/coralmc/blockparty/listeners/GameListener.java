package net.coralmc.blockparty.listeners;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.events.CustomDeathEvent;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.utils.ConfigurationHelper;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class GameListener implements Listener {
    private final BlockParty blockParty;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getBlockY() < ConfigurationHelper.getInt(blockParty, "settings.death-y")) {
            Player player = e.getPlayer();
            Optional<CoralUser> userOptional = Utils.getUser(blockParty, player.getUniqueId());
            userOptional.ifPresent(coralUser -> Bukkit.getPluginManager().callEvent(new CustomDeathEvent(player, coralUser)));

        }
    }
}
