package com.cavetale.quest.entity.behavior;

import com.cavetale.quest.entity.EntityInstance;

public interface EntityBehavior {
    int getPriority();

    boolean apply(EntityInstance entityInstance);

    default void ended(EntityInstance entityInstance) { }
}
