package com.cavetale.quest.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDataScale implements EntityData {
    private double scale = 1;

    @Override
    public boolean apply(Entity entity) {
        if (entity instanceof LivingEntity living) {
            living.getAttribute(Attribute.SCALE).setBaseValue(scale);
            return true;
        } else if (entity instanceof Display display) {
            display.setTransformation(
                new Transformation(
                    new Vector3f(0f, 0f, 0f),
                    new AxisAngle4f(0f, 0f, 0f, 0f),
                    new Vector3f((float) scale, (float) scale, (float) scale),
                    new AxisAngle4f(0f, 0f, 0f, 0f)
                )
            );
            return true;
        } else {
            return false;
        }
    }
}
