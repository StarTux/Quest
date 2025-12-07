package com.cavetale.quest.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public interface EntityTrigger {
    default void onPlayerClick(EntityInstance entityInstance, Player player) { }

    default void onEntityDamage(EntityInstance entityInstance, EntityDamageEvent entityDamageEvent) { }

    default void onEntitySpawned(EntityInstance entityInstance, Entity entity) { }

    default void onEntityRemoved(EntityInstance entityInstance, Entity entity) { }
}
