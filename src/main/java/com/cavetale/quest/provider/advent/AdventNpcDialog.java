package com.cavetale.quest.provider.advent;

import java.util.List;
import java.util.Map;

public record AdventNpcDialog(List<String> dialog, Runnable completionAction, Map<String, String> choices) { }
