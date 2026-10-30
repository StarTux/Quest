package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import com.cavetale.quest.session.PlayerQuest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
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
public final class FallQuestStageWatering extends FallQuestStage {
    private final Component bossBarName = text("Water the Pumpkins", GOLD);
    private final Vec3i goal = Vec3i.vec3i(332, 69, 274);
    private final int amount = 16;

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
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        final Block block = event.getClickedBlock();
        if (block.getType() != Material.PUMPKIN) return;
        final Player player = event.getPlayer();
        if (!Mytems.WATERING_CAN.isItem(player.getInventory().getItemInMainHand())
            && !Mytems.GOLDEN_WATERING_CAN.isItem(player.getInventory().getItemInMainHand())) {
            return;
        }
        final Vec3i vector = Vec3i.of(block);
        if (progress.getVec3iList().contains(vector)) {
            return;
        }
        progress.getVec3iList().add(vector);
        progress.addInteger("progress", 1);
        player.playSound(block.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, SoundCategory.MASTER, 1f, 2f);
        player.spawnParticle(Particle.SPLASH, block.getLocation().add(0.5, 1.0, 0.5), 64, 0.25, 0.25, 0.25, 0);
        if (progress.getInteger("progress") >= amount) {
            final int currentStageIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
            getParent().advanceProgress(playerQuest, currentStageIndex);
        }
    }
}
