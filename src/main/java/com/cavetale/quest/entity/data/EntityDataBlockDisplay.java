package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataBlockDisplay implements EntityData {
    private BlockData blockData;

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof BlockDisplay blockDisplay)) return false;
        blockDisplay.setBlock(blockData);
        return true;
    }
}
