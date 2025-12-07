package com.cavetale.quest.session;

import com.cavetale.quest.QuestPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public final class Sessions {
    private final QuestPlugin plugin;
    private final Map<UUID, Session> sessionMap = new HashMap<>();
    private final SessionListener sessionListener = new SessionListener(this);

    public void enable() {
        sessionListener.enable();
        for (Player player : Bukkit.getOnlinePlayers()) {
            enableSession(player);
        }
    }

    public void disable() {
        for (Session session : sessionMap.values()) {
            session.disable();
        }
        sessionMap.clear();
    }

    public void enableSession(Player player) {
        Session session = new Session(plugin, player.getUniqueId(), player.getName());
        sessionMap.put(session.getUuid(), session);
        session.enable();
    }

    public void disableSession(Player player) {
        Session session = sessionMap.remove(player.getUniqueId());
        if (session != null) session.disable();
    }

    public void tick() {
        for (Session session : sessionMap.values()) {
            if (!session.isEnabled()) continue;
            session.tick();
        }
    }
}
