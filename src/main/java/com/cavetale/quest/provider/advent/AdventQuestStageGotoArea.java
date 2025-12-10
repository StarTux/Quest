package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageGotoArea extends AdventQuestStage {
    private final Component bossBarName;
    private final Cuboid area;
    private BoundingBox areaBoundingBox;

    @Override
    public void enable() {
        areaBoundingBox = area.toBoundingBox();
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return area.getCenter();
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    public void tick(PlayerQuest playerQuest, Progress progress) {
        final Player player = playerQuest.getSession().getPlayer();
        if (player.getIdleDuration().toSeconds() > 1) return;
        if (player == null || !player.getWorld().getName().equals(getParent().getAdventWorldName())) return;
        if (areaBoundingBox.contains(player.getBoundingBox())) {
            getParent().advanceProgress(playerQuest);
        }
    }
}
