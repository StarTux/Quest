package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.behavior.EntityBehaviorFollow;
import com.cavetale.quest.entity.data.EntityDataClearMobGoals;
import com.cavetale.quest.entity.data.EntityDataCow;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Getter
public final class FallQuestStageHerdCow extends FallQuestStage {
    private final Component bossBarName = Component.text("Herd the Cow into the Barn", NamedTextColor.GOLD);
    private final Vec3i goal = Vec3i.vec3i(334, 67, 172);

    @Override
    public void enable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        final EntityInstance entityInstance = new EntityInstance(
            new EntityConfig(EntityType.COW),
            new SpawnLocationConfig(
                FallProvider.SERVER,
                FallProvider.WORLD,
                353.01, 66.00, 320.51
            ),
            Viewership.single(playerQuest.getSession().getUuid())
        );
        entityInstance.getConfig().addEntityBehavior(new EntityBehaviorFollow(1, playerQuest.getSession().getUuid(), 4.0));
        entityInstance.getConfig().addEntityData(new EntityDataClearMobGoals());
        entityInstance.getConfig().addEntityData(new EntityDataCow().cold().moodySound());
        playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
        cache.cows.add(entityInstance);
    }

    @Override
    public void disable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (EntityInstance entityInstance : cache.cows) {
            entityInstance.disable();
        }
        cache.cows.clear();
    }

    @Override
    public void tick(PlayerQuest playerQuest, final Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        if (cache.cows.isEmpty()) return;
        final EntityInstance entityInstance = cache.cows.get(0);
        if (!entityInstance.isSpawned()) return;
        final Location cowLocation = entityInstance.getSpawnedEntity().getLocation();
        if (Vec3i.of(cowLocation).distanceSquared(goal) <= 16) {
            getParent().advanceProgress(playerQuest);
            final Player player = playerQuest.getSession().getPlayer();
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1f, 1f);
            player.spawnParticle(Particle.HAPPY_VILLAGER, cowLocation.add(0, 0.5, 0), 64, 0.35, 0.35, 0.35, 0.0);
        }
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        if (cache.cows.isEmpty()) return Vec3i.ZERO;
        final EntityInstance entityInstance = cache.cows.get(0);
        if (!entityInstance.isSpawned()) {
            return entityInstance.getSpawnLocationConfig().toVec3i();
        }
        final Location cowLocation = entityInstance.getSpawnedEntity().getLocation();
        final Player player = playerQuest.getSession().getPlayer();
        if (!player.getWorld().equals(cowLocation.getWorld())) return Vec3i.ZERO;
        if (cowLocation.distanceSquared(player.getLocation()) > 16) {
            return Vec3i.of(cowLocation);
        }
        return goal;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    @Data
    private static final class Cache {
        private List<EntityInstance> cows = new ArrayList<>();
    }
}
