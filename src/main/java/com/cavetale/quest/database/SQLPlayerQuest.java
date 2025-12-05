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
@Name("player_quests")
@NotNull
public final class SQLPlayerQuest implements SQLRow {
    @Id
    private Integer id;
    @Keyed
    private UUID player;
    private String questId;
    @Text
    private String status;
    private Date started;
    private Date updated;
    private Date expiry;
}
