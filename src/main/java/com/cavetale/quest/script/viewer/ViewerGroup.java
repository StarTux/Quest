package com.cavetale.quest.script.viewer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public final class ViewerGroup implements Viewership {
    private final Set<UUID> group;

    public ViewerGroup(final Collection<UUID> group) {
        this.group = Set.copyOf(group);
    }

    public ViewerGroup(final UUID... viewers) {
        if (viewers.length == 0) {
            throw new IllegalArgumentException("viewers cannot be empty");
        }
        this.group = Set.of(viewers);
    }

    public static ViewerGroup of(final Player... players) {
        if (players.length == 0) {
            throw new IllegalArgumentException("players cannot be empty");
        }
        final Set<UUID> set = new HashSet<>();
        for (Player player : players) {
            set.add(player.getUniqueId());
        }
        return new ViewerGroup(set);
    }

    public static ViewerGroup of(Collection<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("players cannot be empty");
        }
        final Set<UUID> set = new HashSet<>();
        for (Player player : players) {
            set.add(player.getUniqueId());
        }
        return new ViewerGroup(set);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public boolean isViewer(UUID uuid) {
        return group.contains(uuid);
    }

    @Override
    public Set<UUID> getViewers() {
        return Set.copyOf(group);
    }
}
