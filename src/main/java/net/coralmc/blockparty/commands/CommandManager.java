package net.coralmc.blockparty.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.commands.subcommands.*;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.coralmc.blockparty.utils.ConfigHelper.getFormattedString;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final Map<String, SubCommand> commandMap = Maps.newHashMap();
    private final BlockParty blockParty;

    public CommandManager(BlockParty blockParty) {
        this.blockParty = blockParty;
        setup();
    }

    private void setup() {
        commandMap.put("start", new StartSubCommand(blockParty));
        commandMap.put("end", new EndSubCommand(blockParty));
        commandMap.put("status", new StatusSubCommand(blockParty));
        commandMap.put("reload", new ReloadSubCommand(blockParty));
        commandMap.put("setspawn", new SpawnSubCommand(blockParty));
        commandMap.put("setlobby", new LobbySubCommand(blockParty));
        commandMap.put("corners", new CornerSubCommand(blockParty));
        commandMap.put("apply", new ApplySubCommand(blockParty));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getFormattedString(blockParty, "no-args"));
            commandMap.forEach((key, value) -> sender.sendMessage(ConfigHelper.color("&b/bp &f" + value.getSyntax())));
            return true;
        }

        if (commandMap.get(args[0].toLowerCase()) == null) {
            sender.sendMessage(getFormattedString(blockParty, "unknown-arg"));
            return true;
        }

        SubCommand subCommand = commandMap.get(args[0].toLowerCase());

        if (!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(getFormattedString(blockParty, "no-perms", subCommand.getPermission()));
            return true;
        }

        subCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = Lists.newArrayList();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], commandMap.keySet(), completions);
            Collections.sort(completions);
            return completions;
        }

        return completions;
    }
}
