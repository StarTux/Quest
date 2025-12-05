package com.cavetale.quest.script.speaker;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public final class FixedSpeaker implements Speaker {
    private final boolean showDisplayName;
    private final Component displayName;
    private final Location speechBubbleLocation;

    public FixedSpeaker(final Location location, final Component displayName) {
        this.showDisplayName = true;
        this.displayName = displayName;
        this.speechBubbleLocation = location;
    }

    public FixedSpeaker(final Location location) {
        this.showDisplayName = false;
        this.displayName = null;
        this.speechBubbleLocation = location;
    }

    @Override
    public boolean shouldShowDisplayName() {
        return showDisplayName;
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public Location getSpeechBubbleLocation() {
        return speechBubbleLocation;
    }
}
