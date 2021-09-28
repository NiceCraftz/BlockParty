package net.coralmc.blockparty.commands;

import com.google.common.collect.Maps;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.subcommands.EndSubCommand;
import net.coralmc.blockparty.commands.subcommands.ReloadSubCommand;
import net.coralmc.blockparty.commands.subcommands.StartSubCommand;
import net.coralmc.blockparty.commands.subcommands.StatusSubCommand;
import net.coralmc.blockparty.utils.ColorAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

import static net.coralmc.blockparty.utils.ConfigurationHelper.getFormattedString;

public class CommandManager implements CommandExecutor {
    private final Map<String, SubCommand> commandMap = Maps.newHashMap();
    private final BlockParty blockParty;

    public CommandManager(BlockParty blockParty) {
        this.blockParty = blockParty;
        setup();
    }

    private void setup() {
        commandMap.put("start", new StartSubCommand(blockParty));
        commandMap.put("end", new EndSubCommand(blockParty));
        commandMap.put("statuts", new StatusSubCommand(blockParty));
        commandMap.put("reload", new ReloadSubCommand(blockParty));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(getFormattedString(blockParty, "no-args"));
            commandMap.forEach((key, value) -> sender.sendMessage(ColorAPI.color("&b/bp &f" + value.getSyntax())));
            return true;
        }

        if(commandMap.get(args[0].toLowerCase()) == null) {
            sender.sendMessage(getFormattedString(blockParty, "unknown-arg"));
            return true;
        }

        SubCommand subCommand = commandMap.get(args[0].toLowerCase());

        if(!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(getFormattedString(blockParty, "no-perms"));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }
}
