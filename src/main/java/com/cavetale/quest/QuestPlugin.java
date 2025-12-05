package com.cavetale.quest;

import com.cavetale.quest.database.Database;
import com.cavetale.quest.dialog.Dialogs;
import com.cavetale.quest.entity.Entities;
import com.cavetale.quest.provider.advent.AdventProvider;
import com.cavetale.quest.session.Sessions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class QuestPlugin extends JavaPlugin {
    private static QuestPlugin instance;
    // private final QuestCommand questCommand = new QuestCommand(this);
    private final QuestAdminCommand questAdminCommand = new QuestAdminCommand(this);
    private final Database database = new Database(this);
    private final Quests quests = new Quests(this);
    private final Sessions sessions = new Sessions(this);
    private final Entities entities = new Entities(this);
    private final Dialogs dialogs = new Dialogs(this);

    public QuestPlugin() {
        instance = this;
    }

    @Override
    public void onEnable() {
        questAdminCommand.enable();
        // questCommand.enable();
        database.enable();
        quests.enable();
        new AdventProvider(this).enable();
        sessions.enable();
        entities.enable();
        dialogs.enable();
        Bukkit.getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
    }

    @Override
    public void onDisable() {
        entities.disable();
        sessions.disable();
        quests.disable();
        database.disable();
    }

    public static QuestPlugin questPlugin() {
        return instance;
    }

    private void tick() {
        entities.tick();
        sessions.tick();
        dialogs.tick();
    }
}
