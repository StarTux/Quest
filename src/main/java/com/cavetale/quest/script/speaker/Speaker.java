package com.cavetale.quest.script.speaker;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface Speaker {
    boolean shouldShowDisplayName();

    Component getDisplayName();

    Location getSpeechBubbleLocation();

    boolean isEntity(Entity entity);
}
