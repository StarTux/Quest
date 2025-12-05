package com.cavetale.quest.script.viewer;

import java.util.Set;
import java.util.UUID;
import lombok.Value;

@Value
public final class GlobalViewer implements Viewership {
    public static final GlobalViewer INSTANCE = new GlobalViewer();

    public static GlobalViewer globalViewer() {
        return INSTANCE;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public boolean isViewer(UUID uuid) {
        return true;
    }

    @Override
    public Set<UUID> getViewers() {
        return Set.of();
    }
}
