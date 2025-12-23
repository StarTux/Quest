package com.cavetale.quest.database;

import com.winthier.sql.SQLRow;
import com.winthier.sql.SQLRow.Name;
import com.winthier.sql.SQLRow.NotNull;
import com.winthier.sql.SQLRow.UniqueKey;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
@Name("player_memories")
@NotNull
@UniqueKey({"player", "name"})
public final class SQLPlayerMemory implements SQLRow {
    @Id
    private Integer id;
    @Keyed
    private UUID player;
    @VarChar(255)
    private String name;
    @Text
    private String value;
    private Date updated;
    @Nullable
    private Date expiry;

    public SQLPlayerMemory() { }

    public SQLPlayerMemory(final UUID player, final String name, final String value, final Date expiry) {
        this.player = player;
        this.name = name;
        this.value = value;
        this.updated = new Date();
        this.expiry = expiry;
    }

    public SQLPlayerMemory(final UUID player, final String name, final String value) {
        this(player, name, value, null);
    }
}
