package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import net.coralmc.blockparty.files.CoralFile;
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

    @SneakyThrows(Exception.class)
    @Override
    public void execute(CommandSender commandSender, String[] args) {
        CoralFile configFile = blockParty.getConfigFile();
        configFile.getConfiguration().load(configFile.getFile());

        CoralFile locationsFile = blockParty.getLocationsFile();
        locationsFile.getConfiguration().load(locationsFile.getFile());
    }
}
