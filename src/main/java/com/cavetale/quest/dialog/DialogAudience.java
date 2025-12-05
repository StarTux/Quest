package com.cavetale.quest.dialog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public final class DialogAudience {
    public static final DialogAudience GLOBAL = new DialogAudience();
    private final Map<UUID, DialogParticipant> participantMap = new HashMap<>();
    private boolean global;

    static {
        GLOBAL.global = true;
    }

    public DialogParticipant addParticipant(UUID uuid) {
        final DialogParticipant participant = new DialogParticipant(uuid);
        participantMap.put(uuid, participant);
        return participant;
    }

    public DialogParticipant addParticipant(Player player) {
        return addParticipant(player.getUniqueId());
    }

    public DialogParticipant getParticipant(UUID uuid) {
        return participantMap.get(uuid);
    }

    public DialogParticipant getParticipant(Player player) {
        return getParticipant(player.getUniqueId());
    }

    public boolean hasParticipant(UUID uuid) {
        return participantMap.containsKey(uuid);
    }

    public boolean hasParticipant(Player player) {
        return hasParticipant(player.getUniqueId());
    }
}
