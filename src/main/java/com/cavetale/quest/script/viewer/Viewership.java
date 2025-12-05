package com.cavetale.quest.script.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public interface Viewership {
    /**
     * Return true if this is the global viewership, see GlobalView.
     *
     * If true, the result of getViewers() is undefined.
     */
    boolean isGlobal();

    boolean isViewer(UUID uuid);

    default  boolean isViewer(Player player) {
        return isViewer(player.getUniqueId());
    }

    Set<UUID> getViewers();

    default List<Player> getPlayers() {
        final List<Player> players = new ArrayList<>();
        for (UUID uuid : getViewers()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null) players.add(player);
        }
        return players;
    }

    default void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch) {
        if (isGlobal()) {
            location.getWorld().playSound(location, sound, category, volume, pitch);
        } else {
            for (Player player : getPlayers()) {
                player.playSound(location, sound, category, volume, pitch);
            }
        }
    }
}
