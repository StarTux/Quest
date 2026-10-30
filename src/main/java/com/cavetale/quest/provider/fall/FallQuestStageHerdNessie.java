package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.behavior.EntityBehaviorFollow;
import com.cavetale.quest.entity.data.EntityDataClearMobGoals;
import com.cavetale.quest.entity.data.EntityDataScale;
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
public final class FallQuestStageHerdNessie extends FallQuestStage {
    private final Component bossBarName = Component.text("Find Nessie and bring her Home", NamedTextColor.GOLD);
    private final Vec3i goal = Vec3i.vec3i(273, 65, 320);

    @Override
    public void enable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        final EntityInstance entityInstance = new EntityInstance(
            new EntityConfig(EntityType.AXOLOTL),
            new SpawnLocationConfig(
                FallProvider.SERVER,
                FallProvider.WORLD,
                299.07, 65.00, 374.09
            ),
            Viewership.single(playerQuest.getSession().getUuid())
        );
        entityInstance.getConfig().addEntityBehavior(new EntityBehaviorFollow(1, playerQuest.getSession().getUuid(), 4.0));
        entityInstance.getConfig().addEntityData(new EntityDataClearMobGoals());
        entityInstance.getConfig().addEntityData(new EntityDataScale(2.5));
        playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
        cache.mobs.add(entityInstance);
    }

    @Override
    public void disable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (EntityInstance entityInstance : cache.mobs) {
            entityInstance.disable();
        }
        cache.mobs.clear();
    }

    @Override
    public void tick(PlayerQuest playerQuest, final Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        if (cache.mobs.isEmpty()) return;
        final EntityInstance entityInstance = cache.mobs.get(0);
        if (!entityInstance.isSpawned()) return;
        final Location mobLocation = entityInstance.getSpawnedEntity().getLocation();
        if (Vec3i.of(mobLocation).distanceSquared(goal) <= 16) {
            getParent().advanceProgress(playerQuest);
            final Player player = playerQuest.getSession().getPlayer();
            player.playSound(player, Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1f, 1f);
            player.spawnParticle(Particle.HAPPY_VILLAGER, mobLocation.add(0, 0.5, 0), 64, 0.35, 0.35, 0.35, 0.0);
        }
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        if (cache.mobs.isEmpty()) return Vec3i.ZERO;
        final EntityInstance entityInstance = cache.mobs.get(0);
        if (!entityInstance.isSpawned()) {
            return entityInstance.getSpawnLocationConfig().toVec3i();
        }
        final Location mobLocation = entityInstance.getSpawnedEntity().getLocation();
        final Player player = playerQuest.getSession().getPlayer();
        if (!player.getWorld().equals(mobLocation.getWorld())) return Vec3i.ZERO;
        if (mobLocation.distanceSquared(player.getLocation()) > 16) {
            return Vec3i.of(mobLocation);
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
        private List<EntityInstance> mobs = new ArrayList<>();
    }
}
