package com.cavetale.quest.database;

import com.winthier.sql.SQLRow;
import com.winthier.sql.SQLRow.Name;
import com.winthier.sql.SQLRow.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Name("finished_quests")
@NotNull
public final class SQLFinishedQuest implements SQLRow {
    @Id
    private Integer id;
    @Keyed
    private UUID player;
    private String questId;
    private boolean completed;
    private Date started;
    private Date ended;
}
