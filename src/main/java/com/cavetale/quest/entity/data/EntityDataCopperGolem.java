package com.cavetale.quest.entity.data;

import io.papermc.paper.world.WeatheringCopperState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.CopperGolem;
import org.bukkit.entity.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataCopperGolem implements EntityData {
    private WeatheringCopperState weatheringState;
    private CopperGolem.Oxidizing oxidizing = CopperGolem.Oxidizing.waxed();

    public EntityDataCopperGolem(final WeatheringCopperState weatheringState) {
        this.weatheringState = weatheringState;
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof CopperGolem copperGolem)) return false;
        if (weatheringState != null) {
            copperGolem.setWeatheringState(weatheringState);
        }
        if (oxidizing != null) {
            copperGolem.setOxidizing(oxidizing);
        }
        return true;
    }
}
