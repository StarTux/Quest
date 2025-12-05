package com.cavetale.quest.config;

import com.cavetale.quest.script.speaker.Speaker;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Value;

@Data
public final class ScriptConfig {
    private final List<Entry> entries = new ArrayList<>();

    public interface Entry {
        default boolean isBlocking() {
            return true;
        }
    }

    @Value
    public static final class SpeakEntry implements Entry {
        private final boolean blocking;
        private final SpeechBubbleConfig config;
        // TODO actually configurable
        private final Speaker speaker;
    }

    @Value
    public static final class RunnableEntry implements Entry {
        private final Runnable runnable;

        @Override
        public boolean isBlocking() {
            return false;
        }
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }
}
