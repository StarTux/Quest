package com.cavetale.quest.session;

import com.cavetale.quest.database.SQLFinishedQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class FinishedQuest {
    private final SQLFinishedQuest row;

    public String getQuestId() {
        return row.getQuestId();
    }

    public QuestStatus getStatus() {
        return QuestStatus.ofValue(row.getStatus());
    }
}
