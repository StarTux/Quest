package com.cavetale.quest.config;

import com.cavetale.quest.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import net.kyori.adventure.text.Component;

@Data
public final class SpeechBubbleConfig {
    private Component message;
    private boolean automatic;
    private UserPromptType userPrompt = UserPromptType.CONTINUE;
    private List<UserChoice> choices;

    public enum UserPromptType {
        NONE,
        CONTINUE,
        FINAL,
        CHOICE,
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

        public boolean isChoice() {
            return this == CHOICE;
        }
    }

    /**
     * Currently we use the label as the name of the jump target.
     * @param label the label for jumping purposes
     * @param display the displayed text
     */
    public record UserChoice(String label, Component display) { }

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

    public void setUserChoices(final List<UserChoice> theChoices) {
        this.userPrompt = UserPromptType.CHOICE;
        this.choices = List.copyOf(theChoices);
    }

    public void setUserChoices(UserChoice... theChoices) {
        setUserChoices(List.of(theChoices));
    }

    public void addUserChoice(String label, Component display) {
        choices = choices == null
            ? new ArrayList<>()
            : new ArrayList<>(choices);
        choices.add(new UserChoice(label, display));
        this.userPrompt = UserPromptType.CHOICE;
    }
}
