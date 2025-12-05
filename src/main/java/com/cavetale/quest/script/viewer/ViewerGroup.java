package com.cavetale.quest.script.viewer;

import java.util.Set;
import java.util.UUID;

public final class ViewerGroup implements Viewership {
    private final Set<UUID> group;

    public ViewerGroup(final UUID... viewers) {
        if (viewers.length == 0) {
            throw new IllegalArgumentException("viewers cannot be empty");
        }
        this.group = Set.of(viewers);
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
