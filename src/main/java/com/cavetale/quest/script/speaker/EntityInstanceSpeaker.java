package com.cavetale.quest.script.speaker;

import com.cavetale.quest.entity.EntityInstance;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor
public final class EntityInstanceSpeaker implements Speaker {
    private final EntityInstance entityInstance;

    public static EntityInstanceSpeaker of(EntityInstance instance) {
        return new EntityInstanceSpeaker(instance);
    }

    @Override
    public boolean shouldShowDisplayName() {
        return entityInstance.getConfig().getDisplayName() != null;
    }

    @Override
    public Component getDisplayName() {
        return entityInstance.getConfig().getDisplayName();
    }

    @Override
    public Location getSpeechBubbleLocation() {
        final Entity entity = entityInstance.getSpawnedEntity();
        if (entity == null) return null;
        if (entity.getType() == EntityType.CAT) {
            return entity.getLocation().add(0.0, entity.getHeight() * 0.5 + 0.75, 0.0);
        }
        return entity.getLocation().add(0.0, entity.getHeight() + 0.75, 0.0);
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity == entityInstance.getSpawnedEntity();
    }
}
