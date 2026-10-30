package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
@RequiredArgsConstructor
public final class FallQuestStageFeedTrain extends FallQuestStage {
    private final Component bossBarName = text("Feed the Train", GOLD);
    private final Vec3i goal = Vec3i.vec3i(163, 73, 235);
    private final List<Vec3i> goals = List.of(
        Vec3i.vec3i(162, 73, 235),
        Vec3i.vec3i(163, 73, 235),
        Vec3i.vec3i(164, 73, 235)
    );

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return goal;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    @Override
    public void onClickBlock(PlayerQuest playerQuest, Progress progress, PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        final Block block = event.getClickedBlock();
        if (!goals.contains(Vec3i.of(block))) return;
        final Player player = event.getPlayer();
        player.playSound(block.getLocation(), Sound.ENTITY_BLAZE_SHOOT, SoundCategory.MASTER, 1f, 0.5f);
        player.spawnParticle(Particle.SMALL_FLAME, Vec3i.vec3i(163, 73, 234).toCenterLocation(player.getWorld()), 16, 0.25, 0.25, 0.25, 0);
        getParent().advanceProgress(playerQuest);
        event.setCancelled(true);
    }
}
