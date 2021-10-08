package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import org.bukkit.command.CommandSender;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

@RequiredArgsConstructor
public class EndSubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.end";
    }

    @Override
    public String getSyntax() {
        return "end";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        blockParty.getGame().setStatus(BlockPartyStatus.END);
        commandSender.sendMessage(getFormattedString(blockParty, "status-set", blockParty.getGame().getStatus().getName()));
    }
}
