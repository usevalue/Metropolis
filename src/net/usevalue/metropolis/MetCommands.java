package net.usevalue.metropolis;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MetCommands implements CommandExecutor {

    private final Metropolis plugin;

    public MetCommands(Metropolis plugin) {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

}
