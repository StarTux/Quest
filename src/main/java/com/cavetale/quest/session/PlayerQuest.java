package com.cavetale.quest.session;

import com.cavetale.quest.Quest;
import com.cavetale.quest.database.SQLPlayerQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Contains one quest the player is currently playing.
 *
 * When it is loaded from storage, enable must be called.
 */
@Getter
@RequiredArgsConstructor
public final class PlayerQuest {
    private final Quest quest;
    private final SQLPlayerQuest row;

    public void enable() { }

    public void disable() { }
}
