package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.Quest;
import com.cavetale.quest.session.PlayerQuest;
import lombok.Data;
import net.kyori.adventure.bossbar.BossBar;

@Data
public abstract class AdventQuest implements Quest {
    private Advent2025Quest questEnum;

    @Override
    public final String getQuestId() {
        return questEnum.getQuestId();
    }

    public final int getDay() {
        return questEnum.getDay();
    }

    public final int getAdventWorldIndex() {
        return questEnum.getAdventWorldIndex();
    }

    public final String getAdventWorldName() {
        return AdventProvider.ADVENT_WORLDS.get(getAdventWorldIndex());
    }

    public abstract void makeBossBar(PlayerQuest playerQuest, BossBar bossBar);

    public abstract AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc);

    public void onPlayerMoveBlock(PlayerQuest playerQuest, Vec3i vector) { }

    public abstract Vec3i getNextGoal(PlayerQuest playerQuest);
}
