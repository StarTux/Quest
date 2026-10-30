package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class FallQuestStageAuto extends FallQuestStage {
    private final Consumer<Player> callback;

    public FallQuestStageAuto() {
        this(null);
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return Vec3i.vec3i(0, 0, 0);
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(Component.text("???"));
        bossBar.progress(0f);
    }

    @Override
    public void tick(PlayerQuest playerQuest, Progress progress) {
        getParent().advanceProgress(playerQuest);
        if (callback != null) {
            callback.accept(playerQuest.getSession().getPlayer());
        }
    }
}
