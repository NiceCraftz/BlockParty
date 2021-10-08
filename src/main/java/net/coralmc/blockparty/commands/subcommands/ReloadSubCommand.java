package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.utils.GameUtils;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class ReloadSubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.reload";
    }

    @Override
    public String getSyntax() {
        return "reload";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        blockParty.getConfigFile().reload();
        blockParty.getBlocksFile().reload();

        blockParty.getGame().setBlockList(GameUtils.loadBlocks(blockParty));
        commandSender.sendMessage(ConfigHelper.getFormattedString(blockParty, "reloaded"));
    }
}
