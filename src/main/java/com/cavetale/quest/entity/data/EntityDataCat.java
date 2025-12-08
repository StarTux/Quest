package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataCat implements EntityData {
    private Cat.Type catType;
    private DyeColor collarColor;
    private boolean headUp;
    private boolean lyingDown;

    public EntityDataCat(final Cat.Type catType, final DyeColor collarColor) {
        this(catType, collarColor, false, false);
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Cat cat)) return false;
        if (catType != null) cat.setCatType(catType);
        if (collarColor != null) cat.setCollarColor(collarColor);
        cat.setHeadUp(headUp);
        cat.setLyingDown(lyingDown);
        return true;
    }
}
