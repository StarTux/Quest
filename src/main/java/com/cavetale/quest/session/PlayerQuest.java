package com.cavetale.quest.session;

import com.cavetale.core.util.Json;
import com.cavetale.quest.Quest;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.database.SQLFinishedQuest;
import com.cavetale.quest.database.SQLPlayerQuest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Contains one quest the player is currently playing.
 *
 * When a quest is started, call start.
 *
 * After a quest is loaded from storage, call enable.
 */
@Getter
@RequiredArgsConstructor
public final class PlayerQuest {
    private final QuestPlugin plugin;
    private final Session session;
    private final Quest quest;
    private final SQLPlayerQuest row;
    private Map<Class<?>, Object> customData = new HashMap<>();
    private boolean dirty;
    private boolean disabled;

    /**
     * Called when a quest is started for the very first time.
     */
    public void start() {
        quest.startPlayerQuest(this);
        dirty = false;
        plugin.getDatabase().getDatabase().insertAsync(row, v -> { });
    }

    public void enable() {
        quest.enablePlayerQuest(this);
    }

    public void disable() {
        disabled = true;
        quest.disablePlayerQuest(this);
        if (dirty) {
            dirty = false;
            row.setUpdated(new Date());
            plugin.getDatabase().getDatabase().updateAsync(row, v -> { });
        }
    }

    public boolean isActive() {
        return row.getStatus() == 0;
    }

    public void tick() {
        quest.tickPlayerQuest(this);
        if (dirty) {
            dirty = false;
            row.setUpdated(new Date());
            plugin.getDatabase().getDatabase().updateAsync(row, v -> { });
        }
    }

    public QuestStatus getStatus() {
        return QuestStatus.ofValue(row.getStatus());
    }

    public void setTag(Object tag) {
        row.setTag(Json.serialize(tag));
        dirty = true;
    }

    public String getSavedTag() {
        return row.getTag();
    }

    public <T extends Object> T getSavedTag(Class<T> type, Supplier<T> dfl) {
        return Json.deserialize(row.getTag(), type, dfl);
    }

    public void setCustomData(Object o) {
        customData.put(o.getClass(), o);
    }

    public <T extends Object> T getCustomData(Class<T> type, Supplier<T> defaultSupplier) {
        final Object o = customData.get(type);
        if (!type.isInstance(o)) {
            T result = defaultSupplier.get();
            customData.put(type, result);
            return result;
        }
        return type.cast(o);
    }

    public <T extends Object> T getCustomData(Class<T> type) {
        return getCustomData(type, () -> null);
    }

    public void removeCustomData(Class<?> type) {
        customData.remove(type);
    }

    public void completeQuest() {
        row.setStatus(QuestStatus.COMPLETE.getValue());
        dirty = true;
        if (!session.hasCompletedQuest(quest)) {
            quest.onFirstCompletion(this);
        } else {
            quest.onRepeatCompletion(this);
        }
    }

    public void cancelQuest() {
        row.setStatus(QuestStatus.CANCELLED.getValue());
        dirty = true;
    }

    public void expireQuest() {
        row.setStatus(QuestStatus.EXPIRED.getValue());
        dirty = true;
    }

    public void moveToFinishedQuests() {
        saveFinishedQuest();
        deleteRow();
        disable();
    }

    private void saveFinishedQuest() {
        final SQLFinishedQuest newRow = new SQLFinishedQuest();
        newRow.setOldId(row.getId());
        newRow.setPlayer(session.getUuid());
        newRow.setQuestId(quest.getQuestId());
        newRow.setStatus(row.getStatus());
        newRow.setTag(row.getTag());
        newRow.setStarted(row.getStarted());
        newRow.setEnded(new Date());
        plugin.getDatabase().getDatabase().insertAsync(newRow, v -> { });
        session.getFinishedQuests().add(new FinishedQuest(newRow));
    }

    private void deleteRow() {
        plugin.getDatabase().getDatabase().deleteAsync(row, v -> { });
    }
}
