package com.cavetale.quest.dialog;

import com.cavetale.quest.config.DialogTreeConfig;
import lombok.Data;

@Data
public final class DialogTree {
    // Configuration
    private DialogTreeConfig config;
    private DialogAudience audience = DialogAudience.GLOBAL;
    // Runtime
    private boolean disabled;
    private DialogTreeConfig.Entry currentEntry;

    public void enable() {
        if (config.getEntries().isEmpty()) {
            disable();
            return;
        }
        currentEntry = config.getEntries().get(0);
    }

    public void disable() {
    }
}
