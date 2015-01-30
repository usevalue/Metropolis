package net.usevalue.metropolis.commands;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.Metropolis;
import net.usevalue.metropolis.towns.Town;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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
            case "info":
                return false;
            case "new":
                if(args.length==1||args.length>3) return sendHelp(sender, "new");
                else return newPlot(sender, args);
            case "remove":
                if(args.length==1) return sendHelp(sender, "remove");
                return false;
            case "buy":
                if(args.length==1) return sendHelp(sender, "buy");
                return false;
            case "rent":
                if(args.length==1) return sendHelp(sender, "rent");
                return false;
            case "unrent":
                if(args.length==1) return sendHelp(sender,"unrent");
                return false;
            case "set":
                if(args.length<3) return sendHelp(sender,"set");
                return plotSet(sender,args);
            default: return false;
        }
    }

    // Plot creation

    public boolean newPlot(CommandSender sender, String[] args) {
        World world = null;
        String regionId = null;
        if(args.length==3) {
            world = plugin.getServer().getWorld(args[1]);
            regionId=args[2];
        }
        else if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
            regionId=args[1];
        }
        if(world==null) return sendHelp(sender,"new");
        createPlot(sender,world,regionId);
        return true;
    }

    public void createPlot(CommandSender sender, World world, String regionId) {
        ProtectedRegion region = plugin.getWorldGuard().getRegionManager(world).getRegion(regionId);
        if(region==null) {
            sender.sendMessage(regionId+" is not a valid region in "+world.getName()+".");
            return;
        }
        List<String> overlappingTowns = plugin.geographers.get(world.getName()).getOverlappingTowns(region);
        if(overlappingTowns.size()==0) {
            sender.sendMessage("The region in question is not inside a town.");
            return;
        }
        if(overlappingTowns.size()>1) {
            sender.sendMessage("The region in question straddles a town boundary.");
            return;
        }
        Town town = (Town) overlappingTowns.toArray()[0];
        if(sender instanceof ConsoleCommandSender || town.getLeaders().contains(sender.getName())) {
            town.addPlot(region);
            sender.sendMessage("Region "+regionId+" has been made a plot in the town of "+town.getName()+".");
        }
    }

    // Setting plot data

    public boolean plotSet(CommandSender sender, String[] args) {
        switch(args[1]) {
            case "owner":       // Gives plot over to new owner
                return false;
            case "rentable":    // Set whether or not a plot can be rented
                return false;
            case "buyable":     // Set whether or not a plot can be bought
                return false;
            case "rent":        // Set how much rent will be charged each interval
                return false;
            case "price":       // Set how much it costs to buy the plot
                return false;
            default:
                return sendHelp(sender,"set");
        }
    }

    // Help topics

    public boolean sendHelp(CommandSender sender, String topic) {
        switch(topic) {
            case "new":
                sender.sendMessage("/plot new [world] <regionId>");
                sender.sendMessage("Creates a new plot from an existing worldguard region.  Region must be contained by a town where user has permissions.");
                sender.sendMessage("If no world is specified, plugin will search world currently occupied by sender.");
                if(sender instanceof ConsoleCommandSender) sender.sendMessage("When issuing commands from console, a world MUST be specified.");
                return true;
            default:
                return false;

        }
    }
}
