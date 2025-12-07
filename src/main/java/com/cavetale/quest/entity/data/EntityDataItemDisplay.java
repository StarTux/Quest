package com.cavetale.quest.entity.data;

import lombok.Value;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

@Value
public final class EntityDataItemDisplay implements EntityData {
    private ItemStack itemStack;

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof ItemDisplay itemDisplay)) return false;
        itemDisplay.setItemStack(itemStack);
        return true;
    }
}
