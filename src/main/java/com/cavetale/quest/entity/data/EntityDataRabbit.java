package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Rabbit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataRabbit implements EntityData {
    private Rabbit.Type rabbitType;

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Rabbit rabbit)) return false;
        if (rabbitType != null) rabbit.setRabbitType(rabbitType);
        return true;
    }
}
