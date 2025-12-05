package com.cavetale.quest;

public interface Quest {
    String getQuestId();

    default void enable() { }

    default void disable() { }
}
