package com.cavetale.quest.config;

import com.cavetale.quest.util.Text;
import lombok.Data;
import net.kyori.adventure.text.Component;

@Data
public final class SpeechBubbleConfig {
    private Component message;
    private boolean automatic;
    private UserPromptType userPrompt = UserPromptType.CONTINUE;

    public enum UserPromptType {
        NONE,
        CONTINUE,
        FINAL,
        ;

        public boolean hasUserPrompt() {
            return this != NONE;
        }

        public boolean isContinue() {
            return this == CONTINUE;
        }

        public boolean isFinal() {
            return this == FINAL;
        }
    }

    public void setMiniMessage(String mini) {
        message = Text.parseMiniMessage(mini);
    }

    public static SpeechBubbleConfig ofMiniMessage(String in) {
        SpeechBubbleConfig result = new SpeechBubbleConfig();
        result.setMiniMessage(in);
        return result;
    }

    public void setFinalUserPrompt() {
        this.userPrompt = UserPromptType.FINAL;
    }
}
