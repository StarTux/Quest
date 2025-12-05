package com.cavetale.quest.script.speaker;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class PlayerSpeaker implements Speaker {
    private final Player player;

    @Override
    public boolean shouldShowDisplayName() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return player.displayName();
    }

    @Override
    public Location getSpeechBubbleLocation() {
        return player.getLocation().add(0.0, player.getHeight(), 0.0);
    }
}
