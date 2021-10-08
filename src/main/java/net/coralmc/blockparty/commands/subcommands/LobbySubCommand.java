package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.utils.ConfigHelper;
import net.coralmc.blockparty.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LobbySubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.setlobby";
    }

    @Override
    public String getSyntax() {
        return "setlobby";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ConfigHelper.getFormattedString(blockParty, "not-player"));
            return;
        }

        Player player = (Player) commandSender;
        blockParty.getLocationsFile().getConfiguration().set("lobby", Utils.locationToString(player.getLocation()));
        blockParty.getLocationsFile().save();
        blockParty.getLocationsFile().reload();
        player.sendMessage(ConfigHelper.getFormattedString(blockParty, "lobby-set"));
    }
}
