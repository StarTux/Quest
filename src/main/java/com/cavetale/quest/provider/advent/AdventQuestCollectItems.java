package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.behavior.EntityBehaviorSpin;
import com.cavetale.quest.entity.data.EntityDataItemDisplay;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;

@Getter
@RequiredArgsConstructor
public final class AdventQuestCollectItems extends AdventQuest {
    private final List<Vec3i> items;
    private final String itemName;
    private final ItemStack itemStack;
    private final List<Advent2025Npc.Stage> questNpcs;
    private final Advent2025Npc returnNpc;
    private final List<String> returnDialog;

    @Override
    public void startPlayerQuest(PlayerQuest playerQuest) {
        Progress progress = new Progress();
        playerQuest.setCustomData(progress);
        playerQuest.setTag(progress);
        spawnItems(playerQuest);
    }

    @Override
    public void enablePlayerQuest(PlayerQuest playerQuest) {
        Progress progress = playerQuest.getSavedTag(Progress.class, Progress::new);
        playerQuest.setCustomData(progress);
        spawnItems(playerQuest);
    }

    @Override
    public void disablePlayerQuest(PlayerQuest playerQuest) {
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (EntityInstance entityInstance : cache.items) {
            entityInstance.disable();
        }
        cache.items.clear();
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, BossBar bossBar) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        if (!progress.spokenToNpc) {
            final Advent2025Npc.Stage stage = questNpcs.get(progress.nextNpcIndex);
            bossBar.name(textOfChildren(text("Talk to "), stage.npc().getInstance().getConfig().getDisplayName()));
            bossBar.progress(1f);
        } else if (!progress.completeCollection) {
            bossBar.name(text("Collect " + items.size() + " " + itemName));
            bossBar.progress((float) Math.min(progress.progress, items.size()) / (float) items.size());
        } else {
            bossBar.name(textOfChildren(text("Bring everything to "), returnNpc.getInstance().getConfig().getDisplayName()));
            bossBar.progress(1f);
        }
    }

    @Override
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        if (!progress.completeCollection) {
            final Advent2025Npc.Stage stage = questNpcs.get(progress.nextNpcIndex);
            if (npc == stage.npc()) {
                return new AdventNpcDialog(
                    stage.dialog(),
                    () -> {
                        if (!playerQuest.isActive() || playerQuest.isDisabled() || progress.spokenToNpc) return;
                        if (progress.nextNpcIndex < questNpcs.size() - 1) {
                            progress.nextNpcIndex += 1;
                        } else {
                            progress.spokenToNpc = true;
                        }
                        playerQuest.setTag(progress);
                        updateItems(playerQuest);
                    },
                    null
                );
            }
            for (int i = progress.nextNpcIndex - 1; i >= 0; i -= 1) {
                final Advent2025Npc.Stage oldStage = questNpcs.get(i);
                if (npc == oldStage.npc()) {
                    return new AdventNpcDialog(oldStage.dialog(), null, null);
                }
            }
            return null;
        } else if (progress.completeCollection && npc == returnNpc) {
            return new AdventNpcDialog(
                returnDialog,
                () -> {
                    if (!playerQuest.isActive() || playerQuest.isDisabled()) return;
                    playerQuest.setTag(progress);
                    playerQuest.completeQuest();
                },
                null
            );
        } else {
            return null;
        }
    }

    @Override
    public void onPlayerMoveBlock(PlayerQuest playerQuest, Vec3i vector) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        if (progress.completeCollection || !progress.spokenToNpc) return;
        final int index = items.indexOf(vector);
        if (index < 0) return;
        if (progress.hasItem(index)) return;
        progress.collected.put(index, true);
        progress.progress += 1;
        if (progress.progress >= items.size()) {
            progress.completeCollection = true;
        }
        updateItems(playerQuest);
        playerQuest.setTag(progress);
        final Player player = playerQuest.getSession().getPlayer();
        final Location location = vector.toCenterLocation(player.getWorld());
        player.spawnParticle(Particle.DUST, location, 32, 0.2, 0.2, 0.2, 0.0, new Particle.DustOptions(Color.YELLOW, 1f));
        player.playSound(location, Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1f, 1f);
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        if (!progress.spokenToNpc) {
            final Advent2025Npc.Stage stage = questNpcs.get(progress.nextNpcIndex);
            return Vec3i.of(
                (int) Math.floor(stage.npc().getX()),
                (int) Math.floor(stage.npc().getY()),
                (int) Math.floor(stage.npc().getZ())
            );
        } else if (!progress.completeCollection) {
            for (int index = 0; index < items.size(); index += 1) {
                if (!progress.hasItem(index)) {
                    return items.get(index);
                }
            }
            return null;
        } else {
            return Vec3i.of(
                (int) Math.floor(returnNpc.getX()),
                (int) Math.floor(returnNpc.getY()),
                (int) Math.floor(returnNpc.getZ())
            );
        }
    }

    private void spawnItems(PlayerQuest playerQuest) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int index = 0; index < items.size(); index += 1) {
            final Vec3i vec = items.get(index);
            final EntityInstance entityInstance = new EntityInstance(
                new EntityConfig(EntityType.ITEM_DISPLAY),
                new SpawnLocationConfig(
                    AdventProvider.ADVENT_SERVER,
                    getAdventWorldName(),
                    vec.x + 0.5,
                    vec.y + 0.5,
                    vec.z + 0.5
                ),
                Viewership.single(playerQuest.getSession().getUuid())
            );
            entityInstance.getConfig().addEntityData(new EntityDataItemDisplay(itemStack));
            entityInstance.getConfig().addEntityBehavior(new EntityBehaviorSpin(1, 10f));
            playerQuest.getPlugin().getEntities().enableEntityInstance(entityInstance);
            cache.items.add(entityInstance);
            if (!progress.spokenToNpc || progress.hasItem(index)) {
                entityInstance.pause();
            }
        }
    }

    private void updateItems(PlayerQuest playerQuest) {
        final Progress progress = playerQuest.getCustomData(Progress.class);
        final Cache cache = playerQuest.getCustomData(Cache.class, Cache::new);
        for (int index = 0; index < items.size(); index += 1) {
            if (progress.hasItem(index)) {
                cache.items.get(index).pause();
            } else {
                cache.items.get(index).unpause();
            }
        }
    }

    @Data
    private static final class Progress implements Serializable {
        private int nextNpcIndex;
        private boolean spokenToNpc;
        private boolean completeCollection;
        private int progress;
        private Map<Integer, Boolean> collected = new HashMap<>();

        public boolean hasItem(int index) {
            return collected.getOrDefault(index, false);
        }
    }

    @Data
    private static final class Cache {
        private List<EntityInstance> items = new ArrayList<>();
    }
}
