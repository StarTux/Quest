package com.cavetale.quest.entity;

import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.behavior.EntityBehavior;
import com.cavetale.quest.entity.data.EntityData;
import com.cavetale.quest.script.speaker.EntityInstanceSpeaker;
import com.cavetale.quest.script.viewer.Viewership;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import static com.cavetale.quest.QuestPlugin.questPlugin;

/**
 * An entity that is either spawned or waiting to be spawned in the
 * world.
 */
@Data
public final class EntityInstance {
    // Configuration
    private final EntityConfig config;
    private final SpawnLocationConfig spawnLocationConfig;
    private final Viewership viewership;
    // Runtime
    private Entity spawnedEntity;
    private boolean enabled;
    private boolean disabled;
    private boolean paused;
    private EntityBehavior currentBehavior;
    private Map<Class<?>, Object> customData = new HashMap<>();
    private int invalidTicks;

    public EntityInstance(
        final EntityConfig config,
        final SpawnLocationConfig spawnLocationConfig,
        final Viewership viewership
    ) {
        this.config = config;
        this.spawnLocationConfig = spawnLocationConfig;
        this.viewership = viewership;
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

    public void pause() {
        paused = true;
        despawn();
    }

    public void unpause() {
        if (!enabled) return;
        paused = false;
        spawn();
    }

    public void tick() {
        if (paused) {
            return;
        }
        if (spawnedEntity != null && !spawnedEntity.isValid() && !spawnedEntity.isDead()) {
            // This is what happens (apparently) when an entity is
            // invalidated for being in an unloaded chunk. Why
            // EntityRemoveEvent is nowhere to be seen, is unknown.
            despawn();
            spawn();
        }
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
        if (paused) return null;
        if (spawnedEntity != null) return spawnedEntity;
        final Location location = spawnLocationConfig.toLocation();
        if (location == null) return null;
        return spawn(location);
    }

    public void despawn() {
        if (spawnedEntity != null) {
            unregisterEntity(spawnedEntity);
            spawnedEntity.remove();
            applyTrigger(trigger -> trigger.onEntityRemoved(this, spawnedEntity));
            spawnedEntity = null;
        }
    }

    public Entity spawn(Location location) {
        if (paused) return null;
        if (spawnedEntity != null) return spawnedEntity;
        if (!location.isChunkLoaded()) return null;
        spawnedEntity = location.getWorld().spawnEntity(location, config.getEntityType(), SpawnReason.CUSTOM, this::prepareEntity);
        if (spawnedEntity != null) {
            registerEntity(spawnedEntity);
            applyTrigger(trigger -> trigger.onEntitySpawned(this, spawnedEntity));
        }
        return spawnedEntity;
    }

    private void prepareEntity(Entity entity) {
        entity.setPersistent(false);
        for (EntityData data : config.getEntityData()) {
            if (!data.apply(entity)) {
                questPlugin().getLogger().log(
                    Level.SEVERE,
                    "EntityData " + data.getClass().getSimpleName() + " does not apply to entity " + entity.getType(),
                    new IllegalArgumentException("data=" + data)
                );
            }
        }
        if (!viewership.isGlobal()) {
            entity.setVisibleByDefault(false);
            for (Player player : viewership.getPlayers()) {
                player.showEntity(questPlugin(), entity);
            }
        }
        if (config.getDisplayName() != null) {
            entity.customName(config.getDisplayName());
        }
        if (entity instanceof LivingEntity living) {
            living.setBodyYaw(spawnLocationConfig.getYaw());
            living.setCollidable(false);
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
            applyTrigger(trigger -> trigger.onEntityRemoved(this, entity));
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
