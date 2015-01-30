package net.usevalue.metropolis.plots;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.usevalue.metropolis.towns.Town;

public interface Plot {

    public ProtectedRegion getRegion();

    public Town getTown();

}
