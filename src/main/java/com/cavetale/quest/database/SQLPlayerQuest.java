package com.cavetale.quest.database;

import com.winthier.sql.SQLRow;
import com.winthier.sql.SQLRow.Name;
import com.winthier.sql.SQLRow.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
@Name("player_quests")
@NotNull
public final class SQLPlayerQuest implements SQLRow {
    @Id
    private Integer id;
    @Keyed
    private UUID player;
    private String questId;
    private int status;
    @Text
    private String tag;
    private Date started;
    private Date updated;
    @Nullable
    private Date expiry;

    public SQLPlayerQuest() { }

    public SQLPlayerQuest(final UUID player, final String questId, final Date expiry) {
        this.player = player;
        this.questId = questId;
        this.tag = "";
        this.started = new Date();
        this.updated = started;
        this.expiry = expiry;
    }

    public SQLPlayerQuest(final UUID player, final String questId) {
        this(player, questId, null);
    }
}
