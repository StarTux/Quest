package com.cavetale.quest.entity;

import com.cavetale.quest.QuestPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@Getter
@RequiredArgsConstructor
public final class Entities {
    private final QuestPlugin plugin;
    private final List<EntityInstance> entityInstances = new ArrayList<>();
    private final Map<Integer, EntityInstance> entityMap = new HashMap<>();
    private final EntityListener entityListener = new EntityListener(this);

    public void enable() {
        entityListener.enable();
    }

    public void disable() {
        for (EntityInstance entityInstance : entityInstances) {
            entityInstance.disable();
        }
        entityInstances.clear();
    }

    public void tick() {
        entityInstances.removeIf(EntityInstance::isDisabled);
        entityInstances.forEach(EntityInstance::tick);
    }

    public void enableEntityInstance(EntityInstance entityInstance) {
        entityInstances.add(entityInstance);
        entityInstance.enable();
    }

    public void disableEntityInstance(EntityInstance entityInstance) {
        entityInstances.remove(entityInstance);
        entityInstance.disable();
    }

    public EntityInstance getEntityInstance(Entity entity) {
        return entityMap.get(entity.getEntityId());
    }

    /**
     * Internal use only: EntityInstance will call this.
     */
    protected void registerEntity(Entity entity, EntityInstance entityInstance) {
        entityMap.put(entity.getEntityId(), entityInstance);
    }

    /**
     * Internal use only: EntityInstance will call this.
     */
    protected void unregisterEntity(Entity entity) {
        entityMap.remove(entity.getEntityId());
    }
}
