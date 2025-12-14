package com.cavetale.quest.provider.advent;

import com.cavetale.quest.config.SpeechBubbleConfig.UserChoice;
import java.util.List;

public record AdventNpcDialog(List<String> dialog, Runnable completionAction, List<UserChoice> choices) { }

