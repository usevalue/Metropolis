package net.usevalue.metropolis;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
        plugin = this;
        log = getServer().getLogger();
        Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
        if(wg instanceof WorldGuardPlugin) worldguard = (WorldGuardPlugin) wg;
        else this.disable();
        log.log(Level.WARNING,"Metropolis enabled.");
    }

    public void disable() {
        log.log(Level.SEVERE, "Metropolis disabling.");
        getServer().getPluginManager().disablePlugin(this);
    }
}