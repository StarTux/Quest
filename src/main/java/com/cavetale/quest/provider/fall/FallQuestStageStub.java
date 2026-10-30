package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public final class FallQuestStageStub extends FallQuestStage {
    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return Vec3i.vec3i(0, 0, 0);
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(Component.text("???"));
        bossBar.progress(0f);
    }
}
