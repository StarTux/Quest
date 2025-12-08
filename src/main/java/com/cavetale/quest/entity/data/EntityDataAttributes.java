package com.cavetale.quest.entity.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import static com.cavetale.quest.QuestPlugin.questPlugin;

public final class EntityDataAttributes implements EntityData {
    private final Map<Attribute, Double> attributeMap = new HashMap<>();

    public void addAttribute(Attribute attribute, double value) {
        attributeMap.put(attribute, value);
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof LivingEntity living)) return false;
        for (Map.Entry<Attribute, Double> entry : attributeMap.entrySet()) {
            final Attribute attribute = entry.getKey();
            final double value = entry.getValue();
            final AttributeInstance attributeInstance = living.getAttribute(attribute);
            if (attributeInstance == null) {
                questPlugin().getLogger().log(
                    Level.SEVERE,
                    "Attribute " + attribute + " does not apply to entity " + entity.getType(),
                    new IllegalArgumentException("data=" + this)
                );
            }
            living.getAttribute(attribute).setBaseValue(value);
            if (attribute == Attribute.MAX_HEALTH) {
                living.setHealth(value);
            }
        }
        return true;
    }

    public EntityDataAttributes movementSpeed(double value) {
        addAttribute(Attribute.MOVEMENT_SPEED, value);
        return this;
    }

    public EntityDataAttributes health(double value) {
        addAttribute(Attribute.MAX_HEALTH, value);
        return this;
    }

    public EntityDataAttributes scale(double value) {
        addAttribute(Attribute.SCALE, value);
        return this;
    }

    public EntityDataAttributes jumpStrength(double value) {
        addAttribute(Attribute.JUMP_STRENGTH, value);
        return this;
    }
}
