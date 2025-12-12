package com.cavetale.quest.config;

import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.EntityTrigger;
import com.cavetale.quest.entity.behavior.EntityBehavior;
import com.cavetale.quest.entity.data.EntityData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityKnockbackEvent;

@Data
public final class EntityConfig {
    private EntityType entityType;
    private Component displayName;
    private final List<EntityData> entityData = new ArrayList<>();
    private final List<EntityBehavior> behaviors = new ArrayList<>();
    private final List<EntityTrigger> triggers = new ArrayList<>();

    public EntityConfig() { }

    public EntityConfig(final EntityType entityType) {
        this.entityType = entityType;
    }

    public void prepareForUsage() {
        behaviors.sort(Comparator.comparing(EntityBehavior::getPriority));
    }

    public void addEntityData(EntityData data) {
        entityData.add(data);
    }

    public void addEntityBehavior(EntityBehavior behavior) {
        behaviors.add(behavior);
    }

    public void addEntityTrigger(EntityTrigger trigger) {
        triggers.add(trigger);
    }

    public void protectFromDamage() {
        addEntityTrigger(
            new EntityTrigger() {
                @Override
                public void onEntityDamage(EntityInstance entityInstance, EntityDamageEvent entityDamageEvent) {
                    entityDamageEvent.setCancelled(true);
                }

                @Override
                public void onEntityKnockback(EntityInstance entityInstance, EntityKnockbackEvent event) {
                    event.setCancelled(true);
                }
            }
        );
    }
}
