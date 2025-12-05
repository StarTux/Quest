package com.cavetale.quest.entity;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@RequiredArgsConstructor
public final class EntityListener implements Listener {
    private final Entities entities;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, entities.getPlugin());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onEntityRemove(EntityRemoveEvent event) {
        final Entity entity = event.getEntity();
        final EntityInstance entityInstance = entities.getEntityInstance(entity);
        if (entityInstance == null) return;
        entityInstance.onEntityRemove(entity);
        // This event appears to be called when an entity enters cold
        // storage where it isn't ticked anymore, so we want to make
        // sure to remove it for good.
        Bukkit.getScheduler().runTask(entities.getPlugin(), entity::remove);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    private void onPlayerRightClickEntity(PlayerInteractEntityEvent event) {
        if (onClickEntity(event.getPlayer(), event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    private void onPlayerRightClickEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (onClickEntity(player, event.getEntity())) {
            event.setCancelled(true);
        }
    }

    private boolean onClickEntity(Player player, Entity target) {
        final EntityInstance entityInstance = entities.getEntityInstance(target);
        if (entityInstance == null) return false;
        if (entities.getPlugin().getScripts().onPlayerInteractSpeaker(player, target)) {
            return true;
        }
        entityInstance.applyTrigger(trigger -> trigger.onPlayerClick(entityInstance, player));
        return true;
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    private void onEntityDamage(EntityDamageEvent event) {
        final EntityInstance entityInstance = entities.getEntityInstance(event.getEntity());
        if (entityInstance == null) return;
        entityInstance.applyTrigger(trigger -> trigger.onEntityDamage(entityInstance, event));
    }
}
