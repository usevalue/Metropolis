package net.usevalue.metropolis.polities;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.HashSet;
import static net.usevalue.metropolis.Metropolis.plugin;

public class Town implements Polis {

    private String name;
    private ProtectedRegion region;
    private HashSet<OfflinePlayer> leaders;

    public Town(String regionId, World world) {
        this.name=regionId;
        region = plugin.worldguard.getRegionManager(world).getRegion(regionId);
        if(region==null || !(region instanceof ProtectedRegion)) {
            throw new NullPointerException();
        }
        leaders = new HashSet<OfflinePlayer>();
    }

    public String getName() {
        return name;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public HashSet<OfflinePlayer> getLeaders() {
        return leaders;
    }

}
