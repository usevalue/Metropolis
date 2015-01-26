package net.usevalue.metropolis.polities;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.OfflinePlayer;
import java.util.HashSet;
import static net.usevalue.metropolis.Metropolis.plugin;

public class Town implements Polis {

    public String name;
    public final ProtectedRegion region;

    public Town(String name) {
        this.name=name;
        region = null;
    }
    public String getName() {
        return null;
    }

    public HashSet<OfflinePlayer> getLeaders() {
        return null;
    }

    public ProtectedRegion getRegion() {
        return null;
    }
}
