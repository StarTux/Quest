package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataGlow implements EntityData {
    private boolean glowing;
    private Color glowColor;

    @Override
    public boolean apply(Entity entity) {
        entity.setGlowing(glowing);
        if (entity instanceof Display display) {
            display.setGlowColorOverride(glowColor);
        }
        return true;
    }
}
