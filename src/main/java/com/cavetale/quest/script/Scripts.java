package com.cavetale.quest.script;

import com.cavetale.quest.QuestPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Manager for all kinds of scripts and speech bubbles.
 */
@RequiredArgsConstructor
public final class Scripts {
    private final QuestPlugin plugin;
    private final List<SpeechBubble> speechBubbles = new ArrayList<>();
    private final List<Script> scriptList = new ArrayList<>();
    private final Map<Integer, SpeechBubble> entityMap = new HashMap<>();

    public void enable() {
    }

    public void disable() {
        speechBubbles.forEach(SpeechBubble::disable);
        speechBubbles.clear();
        scriptList.forEach(Script::disable);
        scriptList.clear();
    }

    public void enableSpeechBubble(SpeechBubble speechBubble) {
        speechBubbles.add(speechBubble);
        speechBubble.enable();
    }

    public void enableScript(Script script) {
        scriptList.add(script);
        script.enable();
    }

    public void tick() {
        speechBubbles.removeIf(SpeechBubble::isDisabled);
        speechBubbles.forEach(SpeechBubble::tick);
        scriptList.removeIf(Script::isDisabled);
        scriptList.forEach(Script::tick);
    }

    public Script getScriptOfPlayer(UUID uuid) {
        for (Script script : scriptList) {
            if (!script.getViewership().isGlobal() && script.getViewership().isViewer(uuid)) {
                return script;
            }
        }
        return null;
    }

    public Script getScriptOfPlayer(Player player) {
        return getScriptOfPlayer(player.getUniqueId());
    }

    public boolean onPlayerProgressDialog(Player player) {
        for (SpeechBubble speechBubble : speechBubbles) {
            if (speechBubble.getViewership().isViewer(player)) {
                if (speechBubble.onPlayerProgressDialog(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Internal use only: SpeechBubble will call this.
     */
    protected void registerEntity(Entity entity, SpeechBubble speechBubble) {
        entityMap.put(entity.getEntityId(), speechBubble);
    }

    /**
     * Internal use only: SpeechBubble will call this.
     */
    protected void unregisterEntity(Entity entity) {
        entityMap.remove(entity.getEntityId());
    }
}
