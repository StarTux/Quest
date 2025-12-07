package com.cavetale.quest.entity.behavior;

import com.cavetale.quest.entity.EntityInstance;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class EntityBehaviorSpin implements EntityBehavior {
    private final int priority;
    private final float speed;

    @Override
    public boolean apply(EntityInstance entityInstance) {
        final float yaw = entityInstance.getSpawnedEntity().getYaw();
        final float pitch = entityInstance.getSpawnedEntity().getPitch();
        entityInstance.getSpawnedEntity().setRotation(yaw + speed, pitch);
        return true;
    }
}
