package com.cavetale.quest.entity.data;

import lombok.Data;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowman;

@Data
public final class EntityDataSnowman implements EntityData {
    private boolean derp;

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Snowman snowman)) return false;
        snowman.setDerp(derp);
        return true;
    }

    public EntityDataSnowman derp() {
        derp = true;
        return this;
    }
}
