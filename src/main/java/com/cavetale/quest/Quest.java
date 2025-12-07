package com.cavetale.quest;

import com.cavetale.quest.session.PlayerQuest;

public interface Quest {
    String getQuestId();

    default void enable() { }

    default void disable() { }

    default void startPlayerQuest(PlayerQuest playerQuest) { }

    default void enablePlayerQuest(PlayerQuest playerQuest) { }

    default void disablePlayerQuest(PlayerQuest playerQuest) { }

    default void tickPlayerQuest(PlayerQuest playerQuest) { }

    default void onFirstCompletion(PlayerQuest playerQuest) { }

    default void onRepeatCompletion(PlayerQuest playerQuest) { }
}
