package com.cavetale.quest.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestStatus {
    ACTIVE(0),
    COMPLETE(1),
    CANCELLED(2),
    ;

    private final int value;

    public static QuestStatus ofValue(int value) {
        for (var it : values()) {
            if (value == it.value) return it;
        }
        return null;
    }
}
