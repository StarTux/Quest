package com.cavetale.quest.script.viewer;

import java.util.Set;
import java.util.UUID;
import lombok.Value;
import org.bukkit.entity.Player;

@Value
public final class SingleViewer implements Viewership {
    private final UUID viewer;

    public static SingleViewer of(Player player) {
        return new SingleViewer(player.getUniqueId());
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public boolean isViewer(UUID uuid) {
        return viewer.equals(uuid);
    }

    @Override
    public Set<UUID> getViewers() {
        return Set.of(viewer);
    }
}
