package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataFox implements EntityData {
    private Fox.Type foxType;
    private boolean crouching;
    private boolean defending;
    private boolean faceplanted;
    private boolean interested;
    private boolean leaping;
    private boolean sleeping;

    public EntityDataFox(final Fox.Type foxType) {
        this.foxType = foxType;
    }

    public EntityDataFox sleeping() {
        this.sleeping = true;
        return this;
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Fox fox)) return false;
        if (foxType != null) fox.setFoxType(foxType);
        fox.setCrouching(crouching);
        fox.setDefending(defending);
        fox.setFaceplanted(faceplanted);
        fox.setInterested(interested);
        fox.setLeaping(leaping);
        fox.setSleeping(sleeping);
        return true;
    }
}
