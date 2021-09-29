package net.coralmc.blockparty.commands.subcommands;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

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
        blockParty.getFiles().values().forEach(file -> {
            try {
                file.getConfiguration().load(file.getFile());
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        });
    }
}
