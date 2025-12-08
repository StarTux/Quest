package com.cavetale.quest.entity.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;

public final class EntityDataClearMobGoals implements EntityData {
    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Mob mob)) return false;
        Bukkit.getMobGoals().removeAllGoals(mob);
        return true;
    }
}
