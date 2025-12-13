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

    /**
     * A jump target. SpeechBubbleConfig.UserChoice labels jump here.
     */
    @Value
    public static final class LabelEntry implements Entry {
        private final String label;

        @Override
        public boolean isBlocking() {
            return false;
        }
    }

    /**
     * A jump target. SpeechBubbleConfig.UserChoice labels jump here.
     */
    @Value
    public static final class JumpEntry implements Entry {
        private final String label;

        @Override
        public boolean isBlocking() {
            return false;
        }
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }
}
