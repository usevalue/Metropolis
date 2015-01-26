package net.usevalue.metropolis;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Metropolis extends JavaPlugin {

    public static Metropolis plugin;
    public Logger log;
    public WorldGuardPlugin worldguard;

    @Override
    public void onEnable() {

        // The basics
        plugin = this;
        log = getServer().getLogger();
        CommandExecutor cmds = new MetCommands(this);
        getCommand("town").setExecutor(cmds);

        // Load dependencies
        Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
        if(wg instanceof WorldGuardPlugin) worldguard = (WorldGuardPlugin) wg;
        else {
            log.log(Level.SEVERE, "Worldguard not found.  Metropolis disabling");
            this.disable();
        }


        // All good
        log.log(Level.INFO,"Metropolis enabled.");
    }

    public void disable() {
        getServer().getPluginManager().disablePlugin(this);
    }
}