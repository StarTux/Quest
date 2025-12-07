package com.cavetale.quest.entity.data;

import lombok.Value;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

@Value
public final class EntityDataBillboard implements EntityData {
    private Display.Billboard billboard;

    public static EntityDataBillboard center() {
        return new EntityDataBillboard(Display.Billboard.CENTER);
    }

    public static EntityDataBillboard vertical() {
        return new EntityDataBillboard(Display.Billboard.VERTICAL);
    }

    public static EntityDataBillboard horizontal() {
        return new EntityDataBillboard(Display.Billboard.HORIZONTAL);
    }

    public static EntityDataBillboard fixed() {
        return new EntityDataBillboard(Display.Billboard.FIXED);
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Display display)) return false;
        display.setBillboard(billboard);
        return true;
    }
}
