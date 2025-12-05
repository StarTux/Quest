package com.cavetale.quest.script.speaker;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public interface Speaker {
    boolean shouldShowDisplayName();

    Component getDisplayName();

    Location getSpeechBubbleLocation();
}
