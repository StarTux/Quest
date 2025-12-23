package com.cavetale.quest.session;

import com.cavetale.quest.Quest;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.database.SQLFinishedQuest;
import com.cavetale.quest.database.SQLPlayerMemory;
import com.cavetale.quest.database.SQLPlayerQuest;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static com.cavetale.quest.QuestPlugin.questPlugin;

@Data
@RequiredArgsConstructor
public final class Session {
    private final QuestPlugin plugin;
    private final UUID uuid;
    private final String name;
    private boolean enabled;
    private boolean disabled;
    private final List<PlayerQuest> playerQuests = new ArrayList<>();
    private final List<FinishedQuest> finishedQuests = new ArrayList<>();
    private final Map<String, SQLPlayerMemory> memories = new HashMap<>();

    public static Session of(Player player) {
        return questPlugin().getSessions().getSessionMap().get(player.getUniqueId());
    }

    public static boolean isEnabled(Player player) {
        return Session.of(player).isEnabled();
    }

    public static boolean applyIfEnabled(Player player, Consumer<Session> callback) {
        final Session session = of(player);
        if (session == null || !session.enabled) return false;
        callback.accept(session);
        return true;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void enable() {
        plugin.getDatabase().getDatabase().scheduleAsyncTask(this::load);
    }

    public void disable() {
        enabled = false;
        disabled = true;
        for (PlayerQuest playerQuest : playerQuests) {
            playerQuest.disable();
        }
        playerQuests.clear();
        finishedQuests.clear();
    }

    public void tick() {
        playerQuests.removeIf(PlayerQuest::isDisabled);
        playerQuests.forEach(PlayerQuest::tick);
    }

    public void startQuest(Quest quest) {
        plugin.getLogger().info("[Session] " + name + ": Starting Quest: " + quest.getQuestId());
        final PlayerQuest playerQuest = new PlayerQuest(
            plugin,
            this,
            quest,
            new SQLPlayerQuest(uuid, quest.getQuestId())
        );
        playerQuests.add(playerQuest);
        playerQuest.start();
    }

    public List<PlayerQuest> getActiveQuests() {
        final List<PlayerQuest> list = new ArrayList<>();
        for (PlayerQuest playerQuest : playerQuests) {
            if (playerQuest.getStatus() == QuestStatus.ACTIVE) {
                list.add(playerQuest);
            }
        }
        return list;
    }

    public boolean hasActiveQuest(Quest quest) {
        for (PlayerQuest playerQuest : playerQuests) {
            if (
                Objects.equals(playerQuest.getQuest(), quest)
                && playerQuest.getStatus() == QuestStatus.ACTIVE
            ) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCompletedQuest(Quest quest) {
        for (FinishedQuest finishedQuest : finishedQuests) {
            if (
                Objects.equals(finishedQuest.getQuestId(), quest.getQuestId())
                && finishedQuest.getStatus() == QuestStatus.COMPLETE
            ) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActiveOrCompletedQuest(Quest quest) {
        return hasActiveQuest(quest) || hasCompletedQuest(quest);
    }

    /**
     * Load data asynchronously and send them to onLoaded.
     */
    private void load() {
        final List<SQLPlayerQuest> questList = plugin.getDatabase().getDatabase()
            .find(SQLPlayerQuest.class)
            .eq("player", uuid)
            .findList();
        final List<SQLFinishedQuest> finishedQuestList = plugin.getDatabase().getDatabase()
            .find(SQLFinishedQuest.class)
            .eq("player", uuid)
            .findList();
        final List<SQLPlayerMemory> memoryList = plugin.getDatabase().getDatabase()
            .find(SQLPlayerMemory.class)
            .eq("player", uuid)
            .findList();
        Bukkit.getScheduler().runTask(plugin, () -> onLoaded(questList, finishedQuestList, memoryList));
    }

    private void onLoaded(final List<SQLPlayerQuest> questRows, final List<SQLFinishedQuest> finishedQuestRows, final List<SQLPlayerMemory> memoryList) {
        if (disabled) return;
        for (SQLPlayerQuest questRow : questRows) {
            final Quest quest = plugin.getQuests().getQuest(questRow.getQuestId());
            if (quest == null) {
                plugin.getLogger().severe("Quest not found: " + questRow);
                continue;
            }
            PlayerQuest playerQuest = new PlayerQuest(plugin, this, quest, questRow);
            playerQuests.add(playerQuest);
            playerQuest.enable();
        }
        for (SQLFinishedQuest row : finishedQuestRows) {
            final FinishedQuest finishedQuest = new FinishedQuest(row);
            finishedQuests.add(finishedQuest);
        }
        for (SQLPlayerMemory row : memoryList) {
            memories.put(row.getName(), row);
        }
        plugin.getLogger().info(
            "Loaded profile of " + name
            + " quests:" + playerQuests.size()
            + " finished:" + finishedQuests.size()
            + " memories:" + memories.size()
        );
        enabled = true;
    }

    public void storeMemory(String key, String value) {
        final SQLPlayerMemory row = new SQLPlayerMemory(uuid, key, value);
        memories.put(key, row);
        plugin.getDatabase().getDatabase().saveAsync(row, i -> { });
    }

    public void storeMemory(String key, String value, Duration expiry) {
        final SQLPlayerMemory row = new SQLPlayerMemory(uuid, key, value, Date.from(Instant.now().plus(expiry)));
        memories.put(key, row);
        plugin.getDatabase().getDatabase().saveAsync(row, i -> { });
    }

    public boolean hasMemory(String key) {
        return memories.containsKey(key);
    }

    public String getMemory(String key) {
        final SQLPlayerMemory row = memories.get(key);
        if (row == null) return null;
        return row.getValue();
    }

    public int getMemoryAsInt(String key, int defaultValue) {
        final SQLPlayerMemory row = memories.get(key);
        if (row == null) return defaultValue;
        try {
            return Integer.parseInt(row.getValue());
        } catch (IllegalArgumentException iae) {
            return defaultValue;
        }
    }
}
