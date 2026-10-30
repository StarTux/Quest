package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataCow implements EntityData {
    private Cow.Variant cowVariant;
    private Cow.SoundVariant cowSoundVariant;

    public EntityDataCow cold() {
        cowVariant = Cow.Variant.COLD;
        return this;
    }

    public EntityDataCow temperate() {
        cowVariant = Cow.Variant.TEMPERATE;
        return this;
    }

    public EntityDataCow warm() {
        cowVariant = Cow.Variant.WARM;
        return this;
    }

    public EntityDataCow moodySound() {
        cowSoundVariant = Cow.SoundVariant.MOODY;
        return this;
    }

    public EntityDataCow classicSound() {
        cowSoundVariant = Cow.SoundVariant.CLASSIC;
        return this;
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Cow cow)) return false;
        if (cowVariant != null) cow.setVariant(cowVariant);
        if (cowSoundVariant != null) cow.setSoundVariant(cowSoundVariant);
        return true;
    }
}
