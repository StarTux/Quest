package com.cavetale.quest.script;

import com.cavetale.quest.config.ScriptConfig;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import static com.cavetale.quest.QuestPlugin.questPlugin;

@Data
@RequiredArgsConstructor
public final class ScriptWorker {
    private final Script script;
    private final int workerIndex;
    private int entryIndex = 0;
    private List<SpeechBubble> waitingOnSpeechBubble = new ArrayList<>();
    private boolean disabled;

    public void disable() {
        disabled = true;
        waitingOnSpeechBubble.forEach(SpeechBubble::disable);
        waitingOnSpeechBubble.clear();
    }

    public void tick() {
        waitingOnSpeechBubble.removeIf(SpeechBubble::isDisabled);
        if (waitingOnSpeechBubble.isEmpty()) {
            startNextEntry();
        }
    }

    public void startNextEntry() {
        if (script.getConfig().getEntries().size() <= entryIndex) {
            disable();
            return;
        }
        ScriptConfig.Entry entry = script.getConfig().getEntries().get(entryIndex++);
        if (entry instanceof ScriptConfig.SpeakEntry speak) {
            final SpeechBubble speechBubble = new SpeechBubble(
                speak.getConfig(),
                speak.getSpeaker(),
                script.getViewership()
            );
            speechBubble.setParent(script);
            questPlugin().getScripts().enableSpeechBubble(speechBubble);
            if (entry.isBlocking()) {
                waitingOnSpeechBubble.add(speechBubble);
            }
        } else if (entry instanceof ScriptConfig.RunnableEntry runnable) {
            runnable.getRunnable().run();
        } else {
            questPlugin().getLogger().severe("Unknown entry: " + entry.getClass().getName());
            disable();
            return;
        }
        if (!entry.isBlocking()) {
            startNextEntry();
        }
    }
}
