package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.data.EntityData;
import com.cavetale.quest.entity.data.EntityDataGlow;
import com.cavetale.quest.entity.data.EntityDataInvisible;
import com.cavetale.quest.entity.data.EntityDataItemDisplay;
import com.cavetale.quest.entity.data.EntityDataScale;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageClickBlocks extends AdventQuestStage {
    private final Component bossBarName;
    private final List<Vec3i> blocks;

    @Override
    public void enable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int index = 0; index < blocks.size(); index += 1) {
            final Vec3i vector = blocks.get(index);
            final EntityInstance entityInstance = new EntityInstance(
                new EntityConfig(EntityType.ITEM_DISPLAY),
                new SpawnLocationConfig(
                    AdventProvider.ADVENT_SERVER,
                    getParent().getAdventWorldName(),
                    vector.x + 0.5,
                    vector.y + 0.5,
                    vector.z + 0.5
                ),
                Viewership.single(playerQuest.getSession().getUuid())
            );
            entityInstance.getConfig().addEntityData(new EntityDataItemDisplay(new ItemStack(Material.WHITE_CONCRETE)));
            entityInstance.getConfig().addEntityData(new EntityDataInvisible(true));
            entityInstance.getConfig().addEntityData(new EntityDataScale(0.995));
            if (progress.getIntegers().getOrDefault("block" + index, 0) == 0) {
                entityInstance.getConfig().addEntityData(new EntityDataGlow(true, Color.BLACK));
            } else {
                entityInstance.getConfig().addEntityData(new EntityDataGlow(true, randomColor()));
            }
            playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
            cache.blocks.add(entityInstance);
        }
    }

    @Override
    public void disable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        cache.blocks.forEach(EntityInstance::disable);
        cache.blocks.clear();
    }

    public void onPlayerClickBlock(PlayerQuest playerQuest, Player player, Block block) {
        if (!getParent().getAdventWorldName().equals(block.getWorld().getName())) return;
        final Progress progress = getParent().getProgress(playerQuest).getCurrentStageProgress();
        final Vec3i vector = Vec3i.of(block);
        final int index = blocks.indexOf(vector);
        if (index < 0) return;
        if (progress.getIntegers().getOrDefault("block" + index, 0) != 0) return;
        progress.getIntegers().put("block" + index, 1);
        final int intProgress = progress.getIntegers().getOrDefault("progress", 0) + 1;
        progress.getIntegers().put("progress", intProgress);
        getParent().saveProgress(playerQuest);
        final Location location = vector.toCenterLocation(player.getWorld());
        player.spawnParticle(Particle.NOTE, location.clone().add(0, 0.75, 0), 1, 0.0, 0.0, 0.0, 0.0);
        final float pitch = 2f * (float) intProgress / (float) blocks.size();
        player.playSound(location, Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.MASTER, 1f, pitch);
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        final EntityInstance entityInstance = cache.blocks.get(index);
        for (EntityData data : entityInstance.getConfig().getEntityData()) {
            if (data instanceof EntityDataGlow glow) {
                glow.setGlowColor(randomColor());
                break;
            }
        }
        entityInstance.despawn();
        entityInstance.spawn();
        if (intProgress >= blocks.size()) {
            getParent().advanceProgress(playerQuest);
            return;
        }
    }

    private static Color randomColor() {
        return switch (ThreadLocalRandom.current().nextInt(5)) {
        case 0 -> Color.fromRGB(0x8888ff);
        case 1 -> Color.LIME;
        case 2 -> Color.ORANGE;
        case 3 -> Color.fromRGB(0xff88ff);
        default -> Color.YELLOW;
        };
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        for (int index = 0; index < blocks.size(); index += 1) {
            if (progress.getIntegers().getOrDefault("block" + index, 0) == 0) {
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

    @Data
    private static final class Cache {
        private List<EntityInstance> blocks = new ArrayList<>();
    }
}
