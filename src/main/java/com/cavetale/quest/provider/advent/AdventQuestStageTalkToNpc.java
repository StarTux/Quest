package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageTalkToNpc extends AdventQuestStage {
    private final Component bossBarName;
    private final Advent2025Npc npc;
    private final List<String> dialog;
    private final Map<String, String> choices;

    public AdventQuestStageTalkToNpc(
        final Component bossBarName,
        final Advent2025Npc npc,
        final List<String> dialog
    ) {
        this(bossBarName, npc, dialog, null);
    }

    public AdventQuestStageTalkToNpc(
        final Component bossBarName,
        final Advent2025Npc npc,
        final String... dialog
    ) {
        this(bossBarName, npc, List.of(dialog), null);
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return Vec3i.of(
            (int) Math.floor(npc.getX()),
            (int) Math.floor(npc.getY()),
            (int) Math.floor(npc.getZ())
        );
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    @Override
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Progress progress, Advent2025Npc theNpc) {
        if (npc != theNpc) return null;
        if (choices == null) {
            final int oldIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
            return new AdventNpcDialog(dialog, () -> getParent().advanceProgress(playerQuest, oldIndex), null);
        } else {
            return new AdventNpcDialog(dialog, null, choices);
        }
    }
}
