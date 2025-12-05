package com.cavetale.quest.script;

import com.cavetale.quest.config.ScriptConfig;
import com.cavetale.quest.script.viewer.Viewership;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public final class Script {
    // Configuration
    private final ScriptConfig config;
    private final Viewership viewership;
    // Runtime
    private boolean disabled;
    private final List<ScriptWorker> workers = new ArrayList<>();
    int nextWorkerIndex;

    public void enable() {
        if (config.getEntries().isEmpty()) {
            disable();
            return;
        }
        ScriptWorker worker = new ScriptWorker(this, nextWorkerIndex++);
        workers.add(worker);
        worker.startNextEntry();
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
}
