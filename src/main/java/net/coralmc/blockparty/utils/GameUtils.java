package net.coralmc.blockparty.utils;

import com.google.common.collect.Lists;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.game.task.GameTask;
import net.coralmc.blockparty.objects.CoralUser;
import net.coralmc.blockparty.objects.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

import static net.coralmc.blockparty.utils.ConfigHelper.*;

public class GameUtils {

    public static List<CustomBlock> loadBlocks(BlockParty blockParty) {
        final List<CustomBlock> customBlockList = Lists.newArrayList();
        FileConfiguration config = blockParty.getBlocksFile().getConfiguration();
        ConfigurationSection configurationSection = config.getConfigurationSection("blocks");

        for (String s : configurationSection.getKeys(false)) {
            Optional<Material> materialOptional = GameUtils.getMaterial(
                    configurationSection.getString(s + ".material")
            );

            if (!materialOptional.isPresent()) {
                continue;
            }

            CustomBlock customBlock = new CustomBlock(configurationSection.getString(s + ".name"),
                    materialOptional.get(),
                    (byte) configurationSection.getInt(s + ".data"));

            customBlockList.add(customBlock);

            Bukkit.getLogger().info(getFormattedString(blockParty,
                    "block-load",
                    customBlock.getName(),
                    customBlock.getData()));
        }
        return customBlockList;
    }

    private static Optional<Material> getMaterial(String s) {
        for (Material mat : Material.values()) {
            if (mat.name().equalsIgnoreCase(s))
                return Optional.of(mat);
        }
        return Optional.empty();
    }

    public static String[] getPreparedBoard(BlockParty blockParty, CoralUser coralUser, String scoreboard) {
        BlockPartyGame game = blockParty.getGame();
        List<String> workedList = Lists.newArrayList();
        for (String s : getStringList(blockParty, scoreboard + "-scoreboard")) {
            s = s.replace("%left%", game.getGameTask().getPlayersLeft() + "");
            s = s.replace("%round%", game.getGameTask().getRound() + "");
            s = s.replace("%speed%", game.getGameTask().getTime() + "");
            s = s.replace("%coins%", coralUser.getCoins() + "");
            s = s.replace("%player%", coralUser.getPlayer().getName());
            workedList.add(color(s));
        }
        return workedList.toArray(new String[0]);
    }

    public static void updateBoard(BlockParty blockParty, CoralUser coralUser, String scoreboard) {
        BPlayerBoard bPlayerBoard = Netherboard.instance()
                .createBoard(coralUser.getPlayer(), getScoreboardString(blockParty, scoreboard + "-scoreboard-title"));

        bPlayerBoard.setAll(getPreparedBoard(blockParty, coralUser, scoreboard));
    }

    public static void announceDeath(BlockParty blockParty, Player player) {
        BlockPartyGame blockPartyGame = blockParty.getGame();
        GameTask gameTask = blockPartyGame.getGameTask();

        gameTask.setPlayersLeft(gameTask.getPlayersLeft() - 1);
        blockPartyGame.getUserMap().values().forEach(coralUser -> {
            Player coralPlayer = coralUser.getPlayer();

            coralPlayer.sendMessage(getFormattedString(
                    blockParty, "player-death", player.getName(), blockPartyGame.getGameTask().getPlayersLeft()
            ));

            if (coralUser.isSpectator()) return;
            coralPlayer.sendMessage(getFormattedString(blockParty, "point"));
            coralUser.setCoins(coralUser.getCoins() + 1);

            updateBoard(blockParty, coralUser, "game");

            if (blockPartyGame.getGameTask().getPlayersLeft() <= 1) {
                blockPartyGame.setStatus(BlockPartyStatus.END);
            }
        });

    }
}
