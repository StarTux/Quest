package com.cavetale.quest;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * A quest manager.
 */
@RequiredArgsConstructor
public final class Quests {
    private final QuestPlugin plugin;
    private final Map<String, Quest> questMap = new HashMap<>();

    public void enable() {
    }

    public void disable() {
        for (Quest quest : questMap.values()) {
            quest.disable();
        }
        questMap.clear();
    }

    /**
     * This is where quest providers can register their own quests.
     */
    public void enableQuest(Quest quest) {
        questMap.put(quest.getQuestId(), quest);
        quest.enable();
    }

    public Quest getQuest(String questId) {
        return questMap.get(questId);
    }
}
