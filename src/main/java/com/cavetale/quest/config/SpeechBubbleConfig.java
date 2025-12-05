package com.cavetale.quest.config;

import com.cavetale.quest.util.Text;
import lombok.Data;
import net.kyori.adventure.text.Component;

@Data
public final class SpeechBubbleConfig {
    private Component message;
    private boolean disableWhenFinished;

    public void setMiniMessage(String mini) {
        message = Text.parseMiniMessage(mini);
    }
}
