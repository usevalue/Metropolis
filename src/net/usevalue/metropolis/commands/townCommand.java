package net.usevalue.metropolis.commands;

import com.sk89q.worldedit.entity.Player;
import net.usevalue.metropolis.Metropolis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class townCommand implements CommandExecutor {

    private final Metropolis plugin;

    public townCommand(Metropolis plugin) {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length==0) return townInfo(commandSender,null);

        switch(args[0].toLowerCase()) {
            case "help": {
                if (args.length > 1) return sendHelp(commandSender, args[1]);
                return sendHelp(commandSender, "");
            }
            default:
                return false;
        }
    }

    public boolean townInfo(CommandSender sender,String townName) {
        if(townName==null&&!(sender instanceof Player)) {
            sender.sendMessage("You must specify a town: /town info ");
        }
        return false;
    }

    public boolean sendHelp(CommandSender sender, String topic) {
        switch(topic) {
            case "":
                sender.sendMessage("/town subcommands are:");
                sender.sendMessage("TODO");
                return true;
        }
        return false;
    }

}
