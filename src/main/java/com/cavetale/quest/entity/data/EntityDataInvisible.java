package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataInvisible implements EntityData {
    private boolean invisible = true;

    @Override
    public boolean apply(Entity entity) {
        entity.setInvisible(invisible);
        return true;
    }
}
