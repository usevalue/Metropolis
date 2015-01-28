package net.usevalue.metropolis.commands;

import net.usevalue.metropolis.Metropolis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class plotCommand implements CommandExecutor {

    private final Metropolis plugin;

    public plotCommand(Metropolis plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0) return sendHelp(sender,"");
        switch(args[0]) {
            case "help":
                return sendHelp(sender,"");
            case "new":
                return false;
            case "info":
                return false;
            case "set":
                if(args.length==1) return sendHelp(sender,"set");
                return false;
            case "buy":
                return false;
            case "rent":
                return false;
            default: return false;
        }
    }

    public boolean sendHelp(CommandSender sender, String topic) {
        switch(topic) {
            case "":
                return false;

        }
        return false;
    }
}
