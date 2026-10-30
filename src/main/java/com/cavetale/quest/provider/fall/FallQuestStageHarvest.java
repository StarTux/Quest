package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.session.PlayerQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
public final class FallQuestStageHarvest extends FallQuestStage {
    private final Component bossBarName = text("Harvest some Wheat", GOLD);
    private final Vec3i goal = Vec3i.vec3i(266, 67, 182);
    private final int amount = 64;

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return goal;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(Math.max(0f, Math.min(1f, (float) progress.getInteger("progress") / (float) amount)));
    }

    @Override
    public void onClickBlock(PlayerQuest playerQuest, Progress progress, PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        final Block block = event.getClickedBlock();
        if (block.getType() != Material.WHEAT) return;
        progress.addInteger("progress", 1);
        final Player player = event.getPlayer();
        Bukkit.getScheduler().runTask(QuestPlugin.questPlugin(), () -> player.sendBlockChange(block.getLocation(), Material.AIR.createBlockData()));
        player.playSound(block.getLocation(), Sound.BLOCK_CROP_BREAK, SoundCategory.MASTER, 1f, 1f);
        player.spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5), 64, 0.25, 0.25, 0.25, 0, Material.WHEAT.createBlockData());
        if (progress.getInteger("progress") >= amount) {
            final int currentStageIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
            getParent().advanceProgress(playerQuest, currentStageIndex);
        }
    }
}
