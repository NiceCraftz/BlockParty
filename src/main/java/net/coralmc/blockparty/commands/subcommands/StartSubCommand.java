package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.command.CommandSender;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

@RequiredArgsConstructor
public class StartSubCommand implements SubCommand {
    private final BlockParty blockParty;

    @Override
    public String getPermission() {
        return "blockparty.start";
    }

    @Override
    public String getSyntax() {
        return "start";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(blockParty.getGame().getUserMap().size() >= 2)) {
            commandSender.sendMessage(ConfigHelper.getFormattedString(blockParty, "not-enough"));
            return;
        }

        blockParty.getGame().setStatus(BlockPartyStatus.PLAYING);
        commandSender.sendMessage(getFormattedString(blockParty, "status-set", blockParty.getGame().getStatus()));

    }
}
