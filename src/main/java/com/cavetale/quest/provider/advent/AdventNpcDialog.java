package com.cavetale.quest.provider.advent;

import java.util.List;

public record AdventNpcDialog(List<String> dialog, Runnable completionAction) { }
