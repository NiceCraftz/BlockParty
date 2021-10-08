package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.utils.ConfigHelper;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CornerSubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.corners";
    }

    @Override
    public String getSyntax() {
        return "corners";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ConfigHelper.getFormattedString(blockParty, "not-player"));
            return;
        }

        Player player = (Player) commandSender;
        player.getInventory().addItem(Utils.getAxe(blockParty));
        player.sendMessage(ConfigHelper.getFormattedString(blockParty, "axe-add"));
    }
}
