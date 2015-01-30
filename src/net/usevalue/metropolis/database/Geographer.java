package net.usevalue.metropolis.database;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.Metropolis;
import net.usevalue.metropolis.towns.Town;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Geographer {

    private Metropolis plugin;
    private World world;
    private YamlConfiguration config;

    public Geographer(Metropolis plugin, World world) {
        this.plugin=plugin;
        this.world=world;
        loadTowns();
    }

    // Database

    public void loadTowns() {
        File file = new File(plugin.getDataFolder(),"worlds/"+world.getName()+"/towns.yml");
        if (file.exists()) {
            plugin.getLogger().log(Level.INFO,"Loading towns for "+world.getName()+" from file.");
            config = YamlConfiguration.loadConfiguration(file);
        }
        else {
            plugin.getLogger().log(Level.INFO,"No town file found for "+world.getName()+".  Creating new file.");
            config = new YamlConfiguration();
        }
    }

    public boolean saveTowns() {
        File file = new File(plugin.getDataFolder(),"worlds/"+world.getName()+"/towns.yml");
        try {
            config.save(file);
            plugin.log.log(Level.INFO,"Town data saved for "+world.getName()+".");
            return true;
        }
        catch (IOException e) {
            plugin.log.log(Level.SEVERE,"Unable to save town data for "+world.getName()+".");
            return false;
        }
    }

    // Town factory

    public Town getTown(ProtectedRegion region) {
        return new Town (region,this);
    }

    public Town getTown(String id) {
        ProtectedRegion region = plugin.getWorldGuard().getRegionManager(world).getRegion(id);
        if(region!=null&&config.getKeys(false).contains(id)) return getTown(region);
        else return null;
    }

    public Town getTown(Location location) {
        for(ProtectedRegion region:plugin.getWorldGuard().getRegionManager(world).getApplicableRegions(location)) {
            if(getTownIds().contains(region.getId())) return getTown(region);
        }
        return null;
    }

    public boolean addTown(ProtectedRegion region) {
        if(config.getKeys(false).contains(region.getId())) return false;
        config.createSection(region.getId());
        return true;
    }

    // Searches

    public Set<String> getTownIds() {
        return config.getKeys(false);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    // Analytic functions


    public LinkedList<String> getOverlappingTowns(ProtectedRegion region) {
        LinkedList<String> overlappingTowns = new LinkedList<String>();
        ApplicableRegionSet overlappingRegions = plugin.getWorldGuard().getRegionManager(world).getApplicableRegions(region);
        for(ProtectedRegion overlapping:overlappingRegions) {
            overlappingTowns.add(overlapping.getId());
        }
        return overlappingTowns;
    }

}
