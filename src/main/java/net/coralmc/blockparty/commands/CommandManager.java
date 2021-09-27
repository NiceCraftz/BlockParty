package net.coralmc.blockparty.commands;

import com.google.common.collect.Maps;
import net.coralmc.blockparty.BlockParty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.coralmc.blockparty.utils.ColorAPI;

import java.util.Map;

public class CommandManager implements CommandExecutor {
    private final Map<String, SubCommand> commandMap = Maps.newHashMap();
    private final BlockParty blockParty;

    public CommandManager(BlockParty blockParty) {
        this.blockParty = blockParty;
        setup();
    }

    private void setup() {
        commandMap.put("start", new StartSubCommand());
        commandMap.put("end", new EndSubCommand());
        commandMap.put("statuts", new StatusSubCommand());
        commandMap.put("reload", new ReloadSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ColorAPI.color(blockParty, "commands.no-args"));
            commandMap.forEach((key, value) -> sender.sendMessage(ColorAPI.color("&b/bp &f" + value.getSyntax())));
            return true;
        }

        if(commandMap.get(args[0].toLowerCase()) == null) {
            sender.sendMessage(ColorAPI.color(blockParty, "commands.unknown-arg"));
            return true;
        }

        SubCommand subCommand = commandMap.get(args[0].toLowerCase());

        if(!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(ColorAPI.color(blockParty, "commands.no-perms"));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }
}
