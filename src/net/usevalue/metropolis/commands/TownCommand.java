package net.usevalue.metropolis.commands;

import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.Metropolis;
import net.usevalue.metropolis.towns.Town;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.Console;

public class TownCommand implements CommandExecutor {

    private final Metropolis plugin;

    public TownCommand(Metropolis plugin) {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==0) {
            townInfo(sender,null);
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "help": {
                if (args.length > 1) return sendHelp(sender, args[1]);
                return sendHelp(sender, "");
            }
            case "info": {
                Town town = null;
                if(args.length>=2) {
                    town = plugin.getTown(args[1]);
                }
                else if (sender instanceof Player) {
                    town = plugin.geographers.get(((Player) sender).getWorld().getName()).getTown(((Player) sender).getLocation());
                }
                if(town == null) {
                    sender.sendMessage("You must either stand in the borders of a town or specify a valid town with /town info [townId]");
                    return true;
                }
                townInfo(sender,town);
                return true;
            }
            case "list": {
                if(args.length!=2 && sender instanceof ConsoleCommandSender) {
                    sender.sendMessage("Please specify a world with /town list [world]");
                    return true;
                }
                World world=null;
                if(args.length>1) world = plugin.getServer().getWorld(args[1]);
                if(sender instanceof Player) world = ((Player) sender).getWorld();
                if (world==null) sender.sendMessage("Invalid world.");
                else {
                    sender.sendMessage("Town id list for world '"+world.getName()+":");
                    for(String id:plugin.geographers.get(world.getName()).getTownIds()) {
                        sender.sendMessage(id);
                    }
                }
                return true;
            }
            case "create": {
                if(args.length<2) return sendHelp(sender,"create");
                World world = null;
                if(args.length==3) world = plugin.getServer().getWorld(args[2]);
                else if(sender instanceof Player) world = ((Player)sender).getWorld();
                if(world==null) {
                    sender.sendMessage("Please specify a valid world with /town create <regionId> [world]");
                    return true;
                }
                return createTown(sender,args[1],world);
            }
            case "remove": {

            }
            case "set": {
                return false;
            }
            default:
                return false;
        }
    }

    // Town information

    public void townInfo(CommandSender sender,Town town) {
        if(town==null) return;
        sender.sendMessage("");
    }

    // Town creation

    public boolean createTown(CommandSender sender, String regionId, World world) {
        if(!sender.hasPermission("metropolis.town.create")) {
            sender.sendMessage("You lack sufficient permissions to create towns.");
            return true;
        }
        if(plugin.geographers.get(world.getName()).getTownIds().contains(regionId)) {
            sender.sendMessage("That region is already a town.");
            return true;
        }
        RegionManager regionManager = plugin.getWorldGuard().getRegionManager(world);
        ProtectedRegion region = regionManager.getRegion((regionId));
        if(region==null) {
            sender.sendMessage(regionId+" is not a valid region in "+world+".");
            return true;
        }
        if(plugin.geographers.get(world.getName()).getOverlappingTowns(region).size()>0) {
            sender.sendMessage("Region "+regionId+" already overlaps one or more existing towns.");
            return true;
        }
        plugin.geographers.get(world.getName()).addTown(region);
        sender.sendMessage("Town "+regionId+" created in "+world.getName()+".");
        return true;
    }

    // Help

    public boolean sendHelp(CommandSender sender, String topic) {
        switch(topic) {
            case "":
                sender.sendMessage("General use commands are /town info and /town list.");
                sender.sendMessage("Town mayors and admins may use /town set to edit various aspects of towns.");
                sender.sendMessage("Admins may use /town create and /town remove to set up new towns.");
                return true;
            case "create":
                sender.sendMessage("Usage:");
                sender.sendMessage("/town create <regionId> [world]");
            default:
                return false;
        }
    }

}
