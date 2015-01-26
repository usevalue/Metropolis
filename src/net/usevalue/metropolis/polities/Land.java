package net.usevalue.metropolis.polities;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public interface Land {

    public ProtectedRegion getRegion();
    public Town getPolis();

}
