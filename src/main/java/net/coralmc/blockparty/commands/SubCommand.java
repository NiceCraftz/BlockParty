package net.coralmc.blockparty.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String getPermission();
    String getSyntax();
    void execute(CommandSender commandSender, String[] args);
}
