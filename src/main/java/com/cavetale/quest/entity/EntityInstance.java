package com.cavetale.quest.entity;

import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.behavior.EntityBehavior;
import com.cavetale.quest.entity.data.EntityData;
import com.cavetale.quest.script.speaker.EntityInstanceSpeaker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import static com.cavetale.quest.QuestPlugin.questPlugin;

/**
 * An entity that is either spawned or waiting to be spawned in the
 * world.
 */
@Data
public final class EntityInstance {
    // Configuration
    private EntityConfig config;
    private SpawnLocationConfig spawnLocationConfig;
    // Runtime
    private Entity spawnedEntity;
    private boolean enabled;
    private boolean disabled;
    private EntityBehavior currentBehavior;
    private Map<Class<?>, Object> customData = new HashMap<>();

    public EntityInstance() { }

    public EntityInstance(final EntityConfig config, final SpawnLocationConfig spawnLocationConfig) {
        this.config = config;
        this.spawnLocationConfig = spawnLocationConfig;
    }

    /**
     * This should only be called by Entities#enableSpeechBubble.
     */
    public void enable() {
        config.prepareForUsage();
        enabled = true;
        spawn();
    }

    public void disable() {
        enabled = false;
        disabled = true;
        despawn();
    }

    public void tick() {
        if (spawnedEntity == null) {
            spawn();
        }
        if (spawnedEntity != null) {
            for (EntityBehavior behavior : config.getBehaviors()) {
                if (behavior.apply(this)) {
                    if (currentBehavior != null && currentBehavior != behavior) {
                        currentBehavior.ended(this);
                    }
                    currentBehavior = behavior;
                    break;
                }
            }
        }
    }

    public boolean isSpawned() {
        return spawnedEntity != null;
    }

    public Entity spawn() {
        if (spawnedEntity != null) return spawnedEntity;
        final Location location = spawnLocationConfig.toLocation();
        if (location == null) return null;
        return spawn(location);
    }

    public void despawn() {
        if (spawnedEntity != null) {
            unregisterEntity(spawnedEntity);
            spawnedEntity.remove();
            spawnedEntity = null;
        }
    }

    public Entity spawn(Location location) {
        if (spawnedEntity != null) return spawnedEntity;
        if (!location.isChunkLoaded()) return null;
        spawnedEntity = location.getWorld().spawnEntity(location, config.getEntityType(), SpawnReason.CUSTOM, this::prepareEntity);
        if (spawnedEntity != null) {
            registerEntity(spawnedEntity);
        }
        return spawnedEntity;
    }

    private void prepareEntity(Entity entity) {
        entity.setPersistent(false);
        if (config.getDisplayName() != null) {
            entity.customName(config.getDisplayName());
        }
        for (EntityData data : config.getEntityData()) {
            data.apply(entity);
        }
        if (entity instanceof LivingEntity living) {
            living.setBodyYaw(spawnLocationConfig.getYaw());
        }
    }

    public void setCustomData(Object o) {
        customData.put(o.getClass(), o);
    }

    public <T extends Object> T getCustomData(Class<T> type, Supplier<T> defaultSupplier) {
        final Object o = customData.get(type);
        if (!type.isInstance(o)) {
            T result = defaultSupplier.get();
            customData.put(type, result);
            return result;
        }
        return type.cast(o);
    }

    public <T extends Object> T getCustomData(Class<T> type) {
        return getCustomData(type, () -> null);
    }

    public void removeCustomData(Class<?> type) {
        customData.remove(type);
    }

    public void applyTrigger(Consumer<EntityTrigger> callback) {
        for (EntityTrigger trigger : config.getTriggers()) {
            callback.accept(trigger);
        }
    }

    public void onEntityRemove(Entity entity) {
        if (entity.equals(spawnedEntity)) {
            unregisterEntity(spawnedEntity);
            spawnedEntity = null;
        }
    }

    public EntityInstanceSpeaker getSpeaker() {
        return new EntityInstanceSpeaker(this);
    }

    private void registerEntity(Entity entity) {
        questPlugin().getEntities().registerEntity(entity, this);
    }

    private void unregisterEntity(Entity entity) {
        questPlugin().getEntities().unregisterEntity(entity);
    }
}
