package net.coralmc.blockparty.game;

import com.google.common.collect.Lists;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.objects.CustomBlock;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

import static net.coralmc.blockparty.utils.ColorAPI.color;
import static net.coralmc.blockparty.utils.ConfigurationHelper.getFormattedString;
import static net.coralmc.blockparty.utils.ConfigurationHelper.getString;

public class GameUtils {

    public static List<CustomBlock> loadBlocks(BlockParty blockParty) {
        final List<CustomBlock> customBlockList = Lists.newArrayList();
        FileConfiguration config = blockParty.getBlocksFile().getConfiguration();

        for (String s : config.getConfigurationSection("blocks").getKeys(false)) {
            Optional<Material> materialOptional = GameUtils.getMaterial(config.getString(s + ".material"));
            materialOptional.ifPresent(material -> customBlockList.add(
                    new CustomBlock(config.getString(s + ".name"),
                            material, (byte) config.getInt(s + ".data"))));
        }
        return customBlockList;
    }


    private static Optional<Material> getMaterial(String s) {
        for(Material mat : Material.values()) {
            if(mat.name().equalsIgnoreCase(s))
                return Optional.of(mat);
        }
        return Optional.empty();
    }

    public static void announceDeath(BlockParty blockParty, Player player) {
        blockParty.getGame().getUserMap().values().forEach(coralUser -> {
            if (!coralUser.isAlive()) return;

            Player coralPlayer = coralUser.getPlayer();

            coralPlayer.sendMessage(color(
                    getString(blockParty, "events.player-death", player.getName(), blockParty.getGame().getUserMap().size())
            ));

            coralPlayer.sendMessage(getFormattedString(blockParty, "point"));
            coralUser.setCoins(coralUser.getCoins() + 1);
        });
    }

}
