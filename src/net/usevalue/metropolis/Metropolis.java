package net.usevalue.metropolis;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.usevalue.metropolis.commands.plotCommand;
import net.usevalue.metropolis.commands.TownCommand;
import net.usevalue.metropolis.database.Geographer;
import net.usevalue.metropolis.towns.Town;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Metropolis extends JavaPlugin {

    public static Metropolis plugin;
    public Logger log;
    private WorldGuardPlugin worldguard;

    public HashMap<String,Geographer> geographers;

    @Override
    public void onEnable() {

        // The basics
        plugin = this;
        log = getServer().getLogger();
        getCommand("town").setExecutor(new TownCommand(this));
        getCommand("plot").setExecutor(new plotCommand(this));

        // Load dependencies
        Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
        if(wg instanceof WorldGuardPlugin) worldguard = (WorldGuardPlugin) wg;
        else {
            log.log(Level.SEVERE, "Worldguard not found.  Metropolis disabling");
            this.disable();
        }

        // Set up config
        geographers = new HashMap<String,Geographer>();
        for(World world:getServer().getWorlds()) {
            geographers.put(world.getName(),new Geographer(this,world));
        }

        // All good
        log.log(Level.INFO,"Metropolis enabled.");
    }

    public void disable() {
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        for(Geographer geo:geographers.values()) {
            geo.saveTowns();
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        return worldguard;
    }

    public Town getTown(String id) {
        for(Geographer geo:geographers.values()) {
            if(geo.getTown(id)!=null) return geo.getTown(id);
        }
        return null;
    }
}