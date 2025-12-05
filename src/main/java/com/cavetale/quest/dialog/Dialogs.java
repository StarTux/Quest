package com.cavetale.quest.dialog;

import com.cavetale.quest.QuestPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

/**
 * Manager for all kinds of dialogs.
 */
@RequiredArgsConstructor
public final class Dialogs {
    private final QuestPlugin plugin;
    private final List<SpeechBubble> speechBubbles = new ArrayList<>();
    private final List<DialogTree> dialogTrees = new ArrayList<>();
    private final Map<Integer, SpeechBubble> entityMap = new HashMap<>();

    public void enable() {
    }

    public void disable() {
        speechBubbles.forEach(SpeechBubble::disable);
        speechBubbles.clear();
        dialogTrees.forEach(DialogTree::disable);
        dialogTrees.clear();
    }

    public void enableSpeechBubble(SpeechBubble speechBubble) {
        speechBubbles.add(speechBubble);
        speechBubble.enable();
    }

    public void enableDialogTree(DialogTree dialogTree) {
        dialogTrees.add(dialogTree);
        dialogTree.enable();
    }

    public void tick() {
        speechBubbles.removeIf(SpeechBubble::isDisabled);
        speechBubbles.forEach(SpeechBubble::tick);
    }

    public DialogTree getDialogTreeOfPlayer(UUID uuid) {
        for (DialogTree dialogTree : dialogTrees) {
            if (dialogTree.getAudience().hasParticipant(uuid)) {
                return dialogTree;
            }
        }
        return null;
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
