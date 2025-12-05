package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public final class EntityDataScale implements EntityData {
    private double scale = 1;

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return false;
        living.getAttribute(Attribute.SCALE).setBaseValue(scale);
        return true;
    }
}
