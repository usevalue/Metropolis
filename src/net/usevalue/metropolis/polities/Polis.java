package net.usevalue.metropolis.polities;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.OfflinePlayer;

import java.util.HashSet;

public interface Polis {

    public String getName();
    public ProtectedRegion getRegion();
    public HashSet<OfflinePlayer> getLeaders();
}
