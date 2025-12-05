package com.cavetale.quest.config;

import com.cavetale.core.connect.NetworkServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Hold one spawn location.
 *
 * This class may change in the future, or get replaced, to accomodate
 * for multipler server spawn locations or time tables.
 */
@Data
@AllArgsConstructor
public final class SpawnLocationConfig {
    private NetworkServer server;
    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public SpawnLocationConfig() { }

    public SpawnLocationConfig(final Location location) {
        this.server = NetworkServer.current();
        loadLocation(location);
    }

    public SpawnLocationConfig(
        final NetworkServer server,
        final String world,
        final double x,
        final double y,
        final double z
    ) {
        this(server, world, x, y, z, 0f, 0f);
    }

    public World getBukkitWorld() {
        return Bukkit.getWorld(world);
    }

    public void loadLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    /**
     * Produce a location or return null.
     *
     * This method can be used to test if a spawn is desired and
     * possible.
     */
    public Location toLocation() {
        if (server != null && !server.isThisServer()) return null;
        final World w = getBukkitWorld();
        if (w == null) return null;
        final Location result = new Location(w, x, y, z);
        result.setYaw(yaw);
        result.setPitch(pitch);
        return result;
    }

    public void clearPitchAndYaw() {
        yaw = 0;
        pitch = 0;
    }
}
