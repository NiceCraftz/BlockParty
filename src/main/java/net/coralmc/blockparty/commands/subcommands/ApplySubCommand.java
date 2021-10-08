package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.objects.MapCorners;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ApplySubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.corners";
    }

    @Override
    public String getSyntax() {
        return "apply";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) return;
        Player player = (Player) commandSender;

        MapCorners mapCorners = blockParty.getCornersMap().get(player.getUniqueId());
        if (mapCorners == null) {
            player.sendMessage(ConfigHelper.getFormattedString(blockParty, "locations-invalid"));
            return;
        }

        mapCorners.apply(blockParty, commandSender);
        player.sendMessage(ConfigHelper.getFormattedString(blockParty, "locations-set"));
    }
}
