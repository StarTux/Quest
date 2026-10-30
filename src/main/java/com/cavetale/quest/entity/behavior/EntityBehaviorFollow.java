package com.cavetale.quest.entity.behavior;

import com.cavetale.quest.entity.EntityInstance;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityBehaviorFollow implements EntityBehavior {
    private int priority;
    private UUID followEntity;
    private double radius;

    @Override
    public boolean apply(EntityInstance entityInstance) {
        if (!(entityInstance.getSpawnedEntity() instanceof Mob mob)) return false;
        final Entity target = Bukkit.getEntity(followEntity);
        if (target == null) return false;
        if (!target.getWorld().equals(entityInstance.getSpawnedEntity().getWorld())) return false;
        final Location entityLocation = entityInstance.getSpawnedEntity().getLocation();
        final Location targetLocation = target.getLocation();
        if (entityLocation.distance(targetLocation) > radius) return false;
        return mob.getPathfinder().moveTo(target);
    }
}
