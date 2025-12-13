package com.cavetale.quest;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;

public interface Quest {
    String getQuestId();

    default void enable() { }

    default void disable() { }

    void startPlayerQuest(PlayerQuest playerQuest);

    void enablePlayerQuest(PlayerQuest playerQuest);

    void disablePlayerQuest(PlayerQuest playerQuest);

    default void tickPlayerQuest(PlayerQuest playerQuest) { }

    default void onFirstCompletion(PlayerQuest playerQuest) { }

    default void onRepeatCompletion(PlayerQuest playerQuest) { }

    Vec3i getNextGoal(PlayerQuest playerQuest);

    default void onConfirmChoice(PlayerQuest playerQuest, String label) { }
}
