package net.usevalue.metropolis.database;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.Metropolis;
import net.usevalue.metropolis.towns.Town;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Geographer {

    private Metropolis plugin;
    private World world;
    private HashMap<String,Town> towns;
    private YamlConfiguration config;

    public Geographer(Metropolis plugin, World world) {
        this.plugin=plugin;
        this.world=world;
        loadTowns();
    }


    // Database

    public void loadTowns() {
        towns = new HashMap<String,Town>();
        File file = new File(plugin.getDataFolder(),"worlds/"+world.getName()+"/towns.yml");
        if (file.exists()) {
            plugin.getLogger().log(Level.INFO,"Loading towns for "+world.getName()+" from file.");
            config = YamlConfiguration.loadConfiguration(file);
        }
        else {
            plugin.getLogger().log(Level.INFO,"No town file found for "+world.getName()+".  Creating new file.");
            config = new YamlConfiguration();
        }

        for(String regionId:config.getKeys(false)) {
            Town town = makeTown(regionId);
            if(town!=null)
                towns.put(regionId,town);
        }
    }

    private Town makeTown(String regionId) {
        ProtectedRegion region = plugin.getWorldGuard().getRegionManager(world).getRegion(regionId);
        // Verify region exists
        if(validRegion(region)) {
            Town town = new Town(region, world);
            for(String leaderName:config.getStringList(regionId+".leaders")) town.addLeader(leaderName);
            for(String plotId:config.getStringList(regionId+".plots")) {
                ProtectedRegion plotRegion = plugin.getWorldGuard().getRegionManager(world).getRegion(plotId);
                if(validRegion(plotRegion))
                    town.addPlot(region);
            }
            return town;
        }
        else {
            plugin.log.log(Level.WARNING,"Region "+regionId+" not found.  Not creating town.");
            return null;
        }
    }

    public boolean saveTowns() {
        for(Town town:towns.values()) {
            String id = town.getRegion().getId();
            config.set(id+".name",town.getName());
            config.set(id+".leaders",town.getLeaders());
            config.set(id+".plots",town.getPlotIds());
        }
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

    // Informational functions

    public Collection<Town> getTowns() {
        return towns.values();
    }

    public World getWorld() {
        return world;
    }

    public boolean validRegion(ProtectedRegion region) {
        if(region!=null&&region instanceof ProtectedRegion) return true;
        return false;
    }

    // Analytic functions

    public LinkedList<Town> getOverlappingTowns(ProtectedRegion region) {
        LinkedList<Town> overlappingTowns = new LinkedList<Town>();
        ApplicableRegionSet overlappingRegions = plugin.getWorldGuard().getRegionManager(world).getApplicableRegions(region);
        for(ProtectedRegion overlapping:overlappingRegions) {
            overlappingTowns.add(towns.get(overlapping.getId()));
        }
        return overlappingTowns;
    }

}
