package net.usevalue.metropolis.towns;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.plots.Plot;
import org.bukkit.OfflinePlayer;

import java.util.Collection;


public interface Polis {

    public ProtectedRegion getRegion();

    public String getName();
    public void setName(String name);

    public Collection<String> getLeaders();
    public boolean addLeader(String name);
    public boolean removeLeader(String name);

    public Collection<Plot> getPlots();
    public boolean addPlot(ProtectedRegion plotRegion);
    public boolean removePlot(ProtectedRegion plotRegion);

}
