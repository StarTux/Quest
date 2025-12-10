package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.behavior.EntityBehaviorSpin;
import com.cavetale.quest.entity.data.EntityDataItemDisplay;
import com.cavetale.quest.entity.data.EntityDataScale;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageCollectItems extends AdventQuestStage {
    private final Component bossBarName;
    private final List<Vec3i> items;
    private final BiFunction<Integer, Vec3i, ItemStack> itemStackSupplier;

    @Override
    public void enable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int index = 0; index < items.size(); index += 1) {
            final Vec3i vector = items.get(index);
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
            entityInstance.getConfig().addEntityData(new EntityDataItemDisplay(itemStackSupplier.apply(index, vector)));
            entityInstance.getConfig().addEntityData(new EntityDataScale(0.5));
            entityInstance.getConfig().addEntityBehavior(new EntityBehaviorSpin(1, 10f));
            playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
            cache.items.add(entityInstance);
            if (progress.getIntegers().getOrDefault("item" + index, 0) != 0) {
                entityInstance.pause();
            }
        }
    }

    @Override
    public void disable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (EntityInstance entityInstance : cache.items) {
            entityInstance.disable();
        }
        cache.items.clear();
    }

    @Override
    public void tick(PlayerQuest playerQuest, final Progress progress) {
        final Player player = playerQuest.getSession().getPlayer();
        if (player.getIdleDuration().toSeconds() > 1) return;
        if (player == null || !player.getWorld().getName().equals(getParent().getAdventWorldName())) return;
        final BoundingBox bb = player.getBoundingBox();
        final Vector min = bb.getMin();
        final Vector max = bb.getMax();
        final int ax = (int) Math.floor(min.getX());
        final int ay = (int) Math.floor(min.getY());
        final int az = (int) Math.floor(min.getZ());
        final int bx = (int) Math.floor(max.getX());
        final int by = (int) Math.floor(max.getY());
        final int bz = (int) Math.floor(max.getZ());
        for (int z = az; z <= bz; z += 1) {
            for (int y = ay; y <= by; y += 1) {
                for (int x = ax; x <= bx; x += 1) {
                    final Vec3i vector = Vec3i.of(x, y, z);
                    final int index = items.indexOf(vector);
                    if (index < 0) continue;
                    if (progress.getIntegers().getOrDefault("item" + index, 0) != 0) continue;
                    final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
                    cache.items.get(index).pause();
                    progress.getIntegers().put("item" + index, 1);
                    final int intProgress = progress.getIntegers().getOrDefault("progress", 0) + 1;
                    progress.getIntegers().put("progress", intProgress);
                    getParent().saveProgress(playerQuest);
                    final Location location = vector.toCenterLocation(player.getWorld());
                    player.spawnParticle(Particle.DUST, location, 32, 0.2, 0.2, 0.2, 0.0, new Particle.DustOptions(Color.YELLOW, 1f));
                    player.playSound(location, Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1f, 1f);
                    if (intProgress >= items.size()) {
                        getParent().advanceProgress(playerQuest);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        for (int index = 0; index < items.size(); index += 1) {
            if (progress.getIntegers().getOrDefault("item" + index, 0) == 0) {
                return items.get(index);
            }
        }
        return null;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress((float) Math.min(progress.getIntegers().getOrDefault("progress", 0), items.size()) / (float) items.size());
    }

    @Data
    private static final class Cache {
        private List<EntityInstance> items = new ArrayList<>();
    }
}
