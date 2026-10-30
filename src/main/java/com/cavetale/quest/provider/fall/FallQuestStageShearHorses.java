package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.behavior.EntityLookAtPlayerBehavior;
import com.cavetale.quest.entity.data.EntityDataClearMobGoals;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

@Getter
public final class FallQuestStageShearHorses extends FallQuestStage {
    private final Component bossBarName = Component.text("Shear the Zombie Horses", NamedTextColor.GOLD);
    private final List<Vec3i> locations = List.of(
        Vec3i.vec3i(323, 67, 174),
        Vec3i.vec3i(323, 67, 168),
        Vec3i.vec3i(341, 67, 168),
        Vec3i.vec3i(341, 67, 174)
    );

    @Override
    public void enable(PlayerQuest playerQuest, Progress progress) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int i = 0; i < locations.size(); i += 1) {
            final boolean sheared = progress.getInteger("" + i) > 0;
            final Vec3i vector = locations.get(i);
            final EntityInstance entityInstance = new EntityInstance(
                new EntityConfig(sheared ? EntityType.SKELETON_HORSE : EntityType.ZOMBIE_HORSE),
                new SpawnLocationConfig(
                    FallProvider.SERVER,
                    FallProvider.WORLD,
                    vector.x + 0.5, vector.y + 0.0, vector.z + 0.5
                ),
                Viewership.single(playerQuest.getSession().getUuid())
            );
            entityInstance.getConfig().addEntityBehavior(new EntityLookAtPlayerBehavior(1));
            entityInstance.getConfig().addEntityData(new EntityDataClearMobGoals());
            playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
            cache.mobs.add(entityInstance);
        }
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
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        for (int i = 0; i < locations.size(); i += 1) {
            final boolean sheared = progress.getInteger("" + i) > 0;
            if (!sheared) return locations.get(i);
        }
        return locations.get(0);
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(Math.max(0f, Math.min(1f, (float) progress.getInteger("progress") / (float) locations.size())));
    }

    @Override
    public void onClickEntity(PlayerQuest playerQuest, Progress progress, PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Mob mob)) return;
        final Player player = playerQuest.getSession().getPlayer();
        final ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand == null || hand.getType() != Material.SHEARS) return;
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int i = 0; i < cache.getMobs().size(); i += 1) {
            final boolean sheared = progress.getInteger("" + i) > 0;
            if (sheared) continue;
            final EntityInstance entityInstance = cache.mobs.get(i);
            if (!entityInstance.isEntity(mob)) continue;
            progress.addInteger("" + i, 1);
            entityInstance.getConfig().setEntityType(EntityType.SKELETON_HORSE);
            progress.addInteger("progress", 1);
            player.spawnParticle(Particle.EXPLOSION, mob.getEyeLocation(), 1, 0.25, 0.25, 0.25, 0.0);
            player.playSound(mob.getEyeLocation(), Sound.ENTITY_MOOSHROOM_SHEAR, SoundCategory.MASTER, 1f, 1f);
            entityInstance.despawn();
            entityInstance.spawn();
        }
        if (progress.getInteger("progress") >= locations.size()) {
            getParent().advanceProgress(playerQuest);
        }
    }

    @Data
    private static final class Cache {
        private List<EntityInstance> mobs = new ArrayList<>();
    }
}
