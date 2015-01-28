package net.usevalue.metropolis.towns;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.plots.TownPlot;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.*;

import static net.usevalue.metropolis.Metropolis.plugin;

public class Town implements Polis {

    private String name;
    private ProtectedRegion region;
    private HashSet<OfflinePlayer> leaders;
    private HashMap<String,TownPlot> plots;

    public Town(ProtectedRegion region, World world) {
        this.region = region;
        leaders = new HashSet<OfflinePlayer>();
    }



    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if(name.contains(" ")) return false;
        if(plugin.getTown(name)!=null) return false;
        this.name=name;
        return true;
    }



    public ProtectedRegion getRegion() {
        return region;
    }



    public HashSet<OfflinePlayer> getLeaders() {
        return leaders;
    }

    public boolean addLeader(String name) {
        OfflinePlayer player = getPlayer(name);
        if(player!=null&&player instanceof OfflinePlayer) {
            leaders.add(player);
            return true;
        }
        return false;
    }

    public boolean removeLeader(String name) {
        OfflinePlayer player = getPlayer(name);
        if (leaders.contains(player)) {
            leaders.remove(player);
            return true;
        }
        return false;
    }



    public boolean addPlot(ProtectedRegion region) {
        plots.put(region.getId(), new TownPlot(region, this));
        return true;
    }

    public TownPlot getPlot(String id) {
        return plots.get(id);
    }

    public Collection<TownPlot> getPlots() {
        return plots.values();
    }

    public Set<String> getPlotIds() {
        return plots.keySet();
    }




    @SuppressWarnings("deprecation")  //Yes, we're grabbing players by name.
    private OfflinePlayer getPlayer(String name) {
        return plugin.getServer().getPlayer(name);
    }
}
