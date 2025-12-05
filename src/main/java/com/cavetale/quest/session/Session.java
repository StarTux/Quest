package com.cavetale.quest.session;

import com.cavetale.quest.Quest;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.database.SQLFinishedQuest;
import com.cavetale.quest.database.SQLPlayerQuest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final Map<String, FinishedQuest> finishedQuests = new HashMap<>();

    public static Session of(Player player) {
        return questPlugin().getSessions().getSessionMap().get(player.getUniqueId());
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
        enabled = true;
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
        Bukkit.getScheduler().runTask(plugin, () -> onLoaded(questList, finishedQuestList));
    }

    private void onLoaded(final List<SQLPlayerQuest> questRows, final List<SQLFinishedQuest> finishedQuestRows) {
        if (disabled) return;
        for (SQLPlayerQuest questRow : questRows) {
            final Quest quest = plugin.getQuests().getQuest(questRow.getQuestId());
            if (quest == null) {
                plugin.getLogger().severe("Quest not found: " + questRow);
                continue;
            }
            PlayerQuest playerQuest = new PlayerQuest(quest, questRow);
            playerQuests.add(playerQuest);
            playerQuest.enable();
        }
        plugin.getLogger().info(
            "Loaded profile of " + name
            + " quests:" + playerQuests.size()
            + " finished:" + finishedQuests.size()
        );
        enabled = true;
    }
}
