package com.cavetale.quest.entity.behavior;

import com.cavetale.quest.entity.EntityInstance;
import io.papermc.paper.entity.LookAnchor;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityLookAtPlayerBehavior implements EntityBehavior {
    private int priority;
    private double activationRadius = 8;
    private double radius = 12;

    public EntityLookAtPlayerBehavior(final int priority) {
        this.priority = priority;
    }

    @Override
    public boolean apply(EntityInstance entityInstance) {
        double minDistance = 0.0;
        Player target = null;
        for (Entity nearby : entityInstance.getSpawnedEntity().getNearbyEntities(radius, radius, radius)) {
            if (!(nearby instanceof Player player)) continue;
            final double distanceSq = nearby.getLocation().distanceSquared(entityInstance.getSpawnedEntity().getLocation());
            if (distanceSq > radius * radius) {
                continue;
            }
            if (entityInstance.getSpawnedEntity() instanceof LivingEntity living && !living.hasLineOfSight(player)) {
                continue;
            }
            if (target == null || distanceSq < minDistance) {
                target = player;
                minDistance = distanceSq;
            }
        }
        if (target == null) return false;
        final CustomData data = entityInstance.getCustomData(CustomData.class, CustomData::new);
        final boolean alreadyLooking = target.getUniqueId().equals(data.lookingAt);
        if (alreadyLooking || minDistance <= activationRadius * activationRadius) {
            data.lookingAt = target.getUniqueId();
            entityInstance.getSpawnedEntity().lookAt(target.getEyeLocation(), LookAnchor.EYES);
        }
        return true;
    }

    @Override
    public void ended(EntityInstance entityInstance) {
        entityInstance.removeCustomData(CustomData.class);
    }

    private static final class CustomData {
        private UUID lookingAt;
    }
}
