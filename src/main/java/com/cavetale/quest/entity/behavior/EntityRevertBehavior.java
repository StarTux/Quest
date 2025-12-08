package com.cavetale.quest.entity.behavior;

import com.cavetale.quest.entity.EntityInstance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityRevertBehavior implements EntityBehavior {
    private int priority;

    @Override
    public boolean apply(EntityInstance entityInstance) {
        final Entity entity = entityInstance.getSpawnedEntity();
        final float yawFrom = entity.getYaw();
        final float pitchFrom = entity.getPitch();
        final float yawTo = entityInstance.getSpawnLocationConfig().getYaw();
        final float pitchTo = entityInstance.getSpawnLocationConfig().getPitch();
        if (yawFrom == yawTo && pitchFrom == pitchTo) {
            return true;
        }
        entity.setRotation(
            0.5f * yawFrom + 0.5f * yawTo,
            0.5f * yawFrom * 0.5f * pitchTo
        );
        if (entity instanceof LivingEntity living) {
            living.setBodyYaw(0.5f * living.getBodyYaw() + 0.5f * yawTo);
        }
        return true;
    }
}
