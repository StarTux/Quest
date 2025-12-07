package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;

@Getter
@RequiredArgsConstructor
public final class AdventQuestTalkChain extends AdventQuest {
    private final List<Advent2025Npc> npcs;
    private final List<List<String>> dialogs;

    @Override
    public void startPlayerQuest(PlayerQuest playerQuest) {
        Progress progress = new Progress();
        playerQuest.setCustomData(progress);
        playerQuest.setTag(progress);
    }

    @Override
    public void enablePlayerQuest(PlayerQuest playerQuest) {
        Progress progress = playerQuest.getSavedTag(Progress.class, Progress::new);
        playerQuest.setCustomData(progress);
    }

    @Override
    public void disablePlayerQuest(PlayerQuest playerQuest) {
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, BossBar bossBar) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        final Advent2025Npc npc = npcs.get(progress.nextNpcIndex);
        bossBar.name(textOfChildren(text("Talk to "), npc.getInstance().getConfig().getDisplayName()));
        bossBar.progress(1f);
    }

    @Override
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        final Advent2025Npc theNpc = npcs.get(progress.nextNpcIndex);
        final int oldNpcIndex = progress.nextNpcIndex;
        if (npc != theNpc) return null;
        return new AdventNpcDialog(
            dialogs.get(progress.nextNpcIndex),
            () -> {
                if (!playerQuest.isActive() || playerQuest.isDisabled() || progress.nextNpcIndex != oldNpcIndex) return;
                if (progress.nextNpcIndex < npcs.size() - 1) {
                    progress.nextNpcIndex += 1;
                    playerQuest.setTag(progress);
                } else {
                    playerQuest.completeQuest();
                }
            }
        );
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        final Advent2025Npc npc = npcs.get(progress.nextNpcIndex);
        return Vec3i.of(
            (int) Math.floor(npc.getX()),
            (int) Math.floor(npc.getY()),
            (int) Math.floor(npc.getZ())
        );
    }

    @Data
    private static final class Progress implements Serializable {
        private int nextNpcIndex;
    }
}
