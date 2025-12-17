package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageClickBlocks extends AdventQuestStage {
    private final Component bossBarName;
    private final List<Vec3i> blocks;

    public void onPlayerClickBlock(PlayerQuest playerQuest, Player player, Block block) {
        if (!getParent().getAdventWorldName().equals(block.getWorld().getName())) return;
        final Progress progress = getParent().getProgress(playerQuest).getCurrentStageProgress();
        final Vec3i vector = Vec3i.of(block);
        final int index = blocks.indexOf(vector);
        if (index < 0) return;
        if (progress.getIntegers().getOrDefault("item" + index, 0) != 0) return;
        progress.getIntegers().put("item" + index, 1);
        final int intProgress = progress.getIntegers().getOrDefault("progress", 0) + 1;
        progress.getIntegers().put("progress", intProgress);
        getParent().saveProgress(playerQuest);
        final Location location = vector.toCenterLocation(player.getWorld());
        player.spawnParticle(Particle.NOTE, location.clone().add(0, 0.75, 0), 1, 0.0, 0.0, 0.0, 0.0);
        player.playSound(location, Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.MASTER, 1f, 1f);
        if (intProgress >= blocks.size()) {
            getParent().advanceProgress(playerQuest);
            return;
        }
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        for (int index = 0; index < blocks.size(); index += 1) {
            if (progress.getIntegers().getOrDefault("item" + index, 0) == 0) {
                return blocks.get(index);
            }
        }
        return null;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress((float) Math.min(progress.getIntegers().getOrDefault("progress", 0), blocks.size()) / (float) blocks.size());
    }
}
