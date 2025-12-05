package com.cavetale.quest.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public final class DialogTreeConfig {
    private final List<Entry> entries = new ArrayList<>();

    @Data
    public static final class Entry {
        private final SpeechBubbleConfig speechBubbleConfig;
    }

    public Entry addSpeechBubble(SpeechBubbleConfig config) {
        final Entry entry = new Entry(config);
        entries.add(entry);
        return entry;
    }
}
