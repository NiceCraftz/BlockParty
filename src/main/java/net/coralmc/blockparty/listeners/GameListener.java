package net.coralmc.blockparty.listeners;

import me.imbuzz.dev.gamemaker.api.GameStatus;
import me.imbuzz.dev.gamemaker.api.chat.ChatChannel;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.events.CustomDeathEvent;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.utils.GameUtils;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.MapCorners;
import net.coralmc.blockparty.utils.ConfigHelper;
import net.coralmc.blockparty.utils.Utils;
import net.coralmc.blockparty.workloads.objects.TeleportablePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.Optional;

import static net.coralmc.blockparty.utils.ConfigHelper.*;

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

        if (userOptional.isPresent()
                && e.getTo().getBlockY() <= getInt(blockParty, "death-y")
                && game.getStatus() == BlockPartyStatus.PLAYING
                && !userOptional.get().isSpectator()) {
            Bukkit.getPluginManager().callEvent(new CustomDeathEvent(player, userOptional.get()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Optional<CoralUser> userOptional = Utils.getUser(blockParty, player.getUniqueId());
        if (!userOptional.isPresent()) return;

        if (blockParty.getMinigameData().getStatus() == GameStatus.PLAYING) {
            GameUtils.announceDeath(blockParty, player);
        }

        game.getUserMap().remove(player.getUniqueId());
    }

    private void prepare(GameStatus status, Player player, CoralUser coralUser) {
        player.getInventory().clear();

        if (status == GameStatus.PLAYING || status == GameStatus.END) {
            coralUser.setSpectator();
        }

        player.setGameMode(GameMode.SURVIVAL);
        blockParty.getWorkLoadThread().add(new TeleportablePlayer(player,
                getLocation(blockParty, "lobby").add(0.5, 0, 0.5))
        );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        GameStatus status = blockParty.getMinigameData().getStatus();

        CoralUser coralUser = new CoralUser(player);
        game.getUserMap().put(player.getUniqueId(), coralUser);
        prepare(status, player, coralUser);

        if (Bukkit.getOnlinePlayers().size() >= ConfigHelper.getInt(blockParty, "min-players")) {
            blockParty.getGame().setStatus(BlockPartyStatus.LOBBY);
        }
    }

    @EventHandler
    public void onCustomDeath(CustomDeathEvent e) {
        CoralUser user = e.getCoralUser();
        Player userPlayer = e.getPlayer();

        userPlayer.setGameMode(GameMode.SPECTATOR);
        user.setChatChannel(ChatChannel.SPECTATOR);
        userPlayer.sendMessage(getFormattedString(blockParty, "died"));

        blockParty.getWorkLoadThread().add(new TeleportablePlayer(userPlayer,
                getLocation(blockParty, "lobby").add(0.5, 0, 0.5))
        );

        game.getUserMap().get(userPlayer.getUniqueId()).setSpectator(true);
        GameUtils.announceDeath(blockParty, userPlayer);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!e.getPlayer().hasPermission("blockparty.ignorerestrictions")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getWhoClicked().hasPermission("blockparty.ignorerestrictions")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (!player.getItemInHand().isSimilar(Utils.getAxe(blockParty))) return;
        if (!player.hasPermission("blockparty.corners")) return;
        if (action != Action.RIGHT_CLICK_BLOCK || block == null) return;

        Location location = block.getLocation();
        MapCorners mapCorners = blockParty.getCornersMap().get(player.getUniqueId());

        if (mapCorners == null) {
            mapCorners = new MapCorners();
            mapCorners.setSecondLocation(block.getLocation());
            blockParty.getCornersMap().put(player.getUniqueId(), mapCorners);

            player.sendMessage(ConfigHelper.getFormattedString(
                    blockParty, "position-set", location.getX(), location.getZ()
            ));
            return;
        }

        mapCorners.setSecondLocation(block.getLocation());
        blockParty.getCornersMap().replace(player.getUniqueId(), mapCorners);

        player.sendMessage(ConfigHelper.getFormattedString(
                blockParty, "position-set", location.getX(), location.getZ()
        ));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();

        if (!player.hasPermission("blockparty.ignorerestrictions")) {
            e.setCancelled(true);
        }

        if (!player.getItemInHand().isSimilar(Utils.getAxe(blockParty))) return;
        if (!player.hasPermission("blockparty.corners")) return;

        MapCorners mapCorners = blockParty.getCornersMap().get(player.getUniqueId());

        if (mapCorners == null) {
            mapCorners = new MapCorners();
            mapCorners.setFirstLocation(block.getLocation());
            blockParty.getCornersMap().put(player.getUniqueId(), mapCorners);

            player.sendMessage(ConfigHelper.getFormattedString(
                    blockParty, "position-set", location.getX(), location.getZ()
            ));

            return;
        }

        mapCorners.setFirstLocation(block.getLocation());
        blockParty.getCornersMap().replace(player.getUniqueId(), mapCorners);

        player.sendMessage(ConfigHelper.getFormattedString(
                blockParty, "position-set", location.getX(), location.getZ()
        ));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPermission("blockparty.ignorerestrictions")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }
}
