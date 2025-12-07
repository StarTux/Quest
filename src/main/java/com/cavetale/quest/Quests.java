package com.cavetale.quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
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
    public boolean enableQuest(Quest quest) {
        if (quest.getQuestId() == null) {
            plugin.getLogger().log(Level.SEVERE, "Quest without id", new IllegalArgumentException("quest=" + quest));
            return false;
        }
        if (questMap.containsKey(quest.getQuestId())) {
            plugin.getLogger().log(Level.SEVERE, "Duplicate quest id", new IllegalArgumentException("id=" + quest.getQuestId()));
            return false;
        }
        plugin.getLogger().info("[Quests] Registering: " + quest.getQuestId());
        questMap.put(quest.getQuestId(), quest);
        quest.enable();
        return true;
    }

    public Quest getQuest(String questId) {
        return questMap.get(questId);
    }

    public Set<String> getAllQuestIds() {
        return Set.copyOf(questMap.keySet());
    }
}
