package com.cavetale.quest.script.speaker;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

@RequiredArgsConstructor
public final class EntitySpeaker implements Speaker {
    private final Entity entity;

    @Override
    public boolean shouldShowDisplayName() {
        return entity.customName() != null;
    }

    @Override
    public Component getDisplayName() {
        return entity.customName();
    }

    @Override
    public Location getSpeechBubbleLocation() {
        return entity.getLocation().add(0.0, entity.getHeight(), 0.0);
    }
}
