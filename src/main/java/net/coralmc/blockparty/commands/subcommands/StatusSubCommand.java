package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import me.imbuzz.dev.gamemaker.api.GameStatus;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import org.bukkit.command.CommandSender;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

@RequiredArgsConstructor
public class StatusSubCommand implements SubCommand {
    private final BlockParty blockParty;


    @Override
    public String getPermission() {
        return "blockparty.status";
    }

    @Override
    public String getSyntax() {
        return "status";
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        GameStatus gameStatus = blockParty.getMinigameData().getStatus();
        commandSender.sendMessage(getFormattedString(blockParty, "status-format", gameStatus.toString()));
    }
}
