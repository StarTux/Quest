package com.cavetale.quest.database;

import com.cavetale.quest.QuestPlugin;
import com.winthier.sql.SQLDatabase;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Database {
    private final QuestPlugin plugin;
    private SQLDatabase database;

    public void enable() {
        database = new SQLDatabase(plugin);
        database.registerTables(
            List.of(
                SQLPlayerQuest.class,
                SQLFinishedQuest.class
            )
        );
        database.createAllTables();
    }

    public void disable() {
        database.waitForAsyncTask();
        database.close();
    }
}
