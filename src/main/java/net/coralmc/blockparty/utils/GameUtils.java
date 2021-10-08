package net.coralmc.blockparty.utils;

import com.google.common.collect.Lists;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.objects.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

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

            Bukkit.getLogger().info(ConfigHelper.getFormattedString(blockParty,
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

    public static void announceDeath(BlockParty blockParty, Player player) {
        blockParty.getGame().getUserMap().values().forEach(coralUser -> {
            Player coralPlayer = coralUser.getPlayer();

            coralPlayer.sendMessage(getFormattedString(
                    blockParty, "player-death", player.getName(), blockParty.getGame().getUserMap().size()
            ));

            if (coralUser.isSpectator()) return;
            coralPlayer.sendMessage(getFormattedString(blockParty, "point"));
            coralUser.setCoins(coralUser.getCoins() + 1);
        });
    }

}
