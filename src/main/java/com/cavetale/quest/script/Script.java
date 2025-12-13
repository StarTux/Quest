package com.cavetale.quest.script;

import com.cavetale.quest.config.ScriptConfig;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public final class Script {
    // Configuration
    private final ScriptConfig config;
    private final Viewership viewership;
    // Optional
    private PlayerQuest playerQuest;
    // Runtime
    private boolean disabled;
    private boolean cancelled;
    private final List<ScriptWorker> workers = new ArrayList<>();
    private int nextWorkerIndex;

    public void enable() {
        if (config.getEntries().isEmpty()) {
            disable();
            return;
        }
        ScriptWorker worker = new ScriptWorker(this, nextWorkerIndex++);
        workers.add(worker);
        worker.startCurrentEntry();
    }

    public void cancel() {
        cancelled = true;
        disable();
    }

    public void disable() {
        disabled = true;
    }

    public void tick() {
        workers.removeIf(ScriptWorker::isDisabled);
        workers.forEach(ScriptWorker::tick);
        if (workers.isEmpty()) {
            disable();
        }
    }

    public void onConfirmChoice(SpeechBubble bubble, String label) {
        final int entryIndex = findScriptLabel(label);
        if (entryIndex >= 0) {
            for (ScriptWorker worker : workers) {
                if (worker.getWaitingOnSpeechBubble().contains(bubble)) {
                    worker.jumpToEntry(entryIndex);
                }
            }
        }
        if (playerQuest != null) {
            playerQuest.getQuest().onConfirmChoice(playerQuest, label);
        }
    }

    public int findScriptLabel(String label) {
        for (int i = 0; i < config.getEntries().size(); i += 1) {
            if (config.getEntries().get(i) instanceof ScriptConfig.LabelEntry labelEntry && label.equals(labelEntry.getLabel())) {
                return i;
            }
        }
        return -1;
    }
}
