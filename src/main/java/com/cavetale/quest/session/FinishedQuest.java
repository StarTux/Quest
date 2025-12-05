package com.cavetale.quest.session;

import com.cavetale.quest.database.SQLFinishedQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class FinishedQuest {
    private final SQLFinishedQuest row;
}
