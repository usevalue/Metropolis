package net.usevalue.metropolis.towns;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.database.Geographer;
import net.usevalue.metropolis.plots.Plot;
import org.bukkit.OfflinePlayer;

import java.util.*;

import static net.usevalue.metropolis.Metropolis.plugin;

public class Town implements Polis {

    public final ProtectedRegion region;
    public final Geographer geo;

    public Town(ProtectedRegion region, Geographer geo) {
        this.region = region;
        this.geo=geo;
    }

    @Override
    public ProtectedRegion getRegion() {
        return region;
    }

    @Override
    public String getName() {
        return geo.getConfig().getString(region.getId()+".Name");
    }

    @Override
    public void setName(String name) {
        geo.getConfig().set(region.getId() + ".Name", name);
    }

    @Override
    public List<String> getLeaders() {
        LinkedList<String> leaders = new LinkedList<String>();
        for(String name:geo.getConfig().getStringList(region.getId()+".Leaders")) {
            leaders.add(name);
        }
        return leaders;
    }

    @Override
    public boolean addLeader(String name) {
        OfflinePlayer leader = getPlayer(name);
        Collection<String> leaders = getLeaders();
        if(leader==null||leaders.contains(name)) return false;
        else {
            leaders.add(name);
            geo.getConfig().set(region.getId()+".Leaders",leaders);
            return true;
        }
    }

    @Override
    public boolean removeLeader(String name) {
        List<String> leaders = getLeaders();
        if(leaders.contains(name)) {
            leaders.remove(name);
            geo.getConfig().set(region.getId()+".Leaders",leaders);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Plot> getPlots() {
        return null;
    }

    @Override
    public boolean addPlot(ProtectedRegion plotRegion) {
        return false;
    }

    @Override
    public boolean removePlot(ProtectedRegion plotRegion) {
        return false;
    }


    @SuppressWarnings("deprecation")  //Yes, we're grabbing players by name.
    private OfflinePlayer getPlayer(String name) {
        return plugin.getServer().getPlayer(name);
    }
}
