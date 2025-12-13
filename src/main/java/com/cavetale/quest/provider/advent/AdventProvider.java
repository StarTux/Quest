package com.cavetale.quest.provider.advent;

import com.cavetale.core.connect.NetworkServer;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.ScriptConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.EntityTrigger;
import com.cavetale.quest.entity.behavior.EntityLookAtPlayerBehavior;
import com.cavetale.quest.entity.behavior.EntityRevertBehavior;
import com.cavetale.quest.entity.data.EntityDataAttributes;
import com.cavetale.quest.entity.data.EntityDataCat;
import com.cavetale.quest.entity.data.EntityDataClearMobGoals;
import com.cavetale.quest.entity.data.EntityDataScale;
import com.cavetale.quest.entity.data.EntityProfileData;
import com.cavetale.quest.script.Script;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public final class AdventProvider {
    public static final NetworkServer ADVENT_SERVER = NetworkServer.BETA.isThisServer()
        ? NetworkServer.BETA
        : NetworkServer.FESTIVAL;
    public static final String ADVENT_WORLD_1 = "advent_2025_01";
    public static final List<String> ADVENT_WORLDS = List.of(
        ADVENT_WORLD_1
    );
    @Getter
    private static AdventProvider instance;
    private final QuestPlugin plugin;

    public void enable() {
        instance = this;
        for (Advent2025Npc npc : Advent2025Npc.values()) {
            final EntityInstance inst = new EntityInstance(
                new EntityConfig(npc.getEntityType()),
                new SpawnLocationConfig(
                    ADVENT_SERVER,
                    ADVENT_WORLDS.get(npc.getWorldNumber() - 1),
                    npc.getX(), npc.getY(), npc.getZ(),
                    npc.getYaw(), 0f
                ),
                Viewership.global()
            );
            inst.getConfig().setDisplayName(npc.getDisplayName());
            if (npc.getEntityType() == EntityType.MANNEQUIN) {
                inst.getConfig().addEntityData(new EntityProfileData(npc.getTexture(), null));
            } else {
                inst.getConfig().addEntityData(new EntityDataAttributes().movementSpeed(0).jumpStrength(0));
                inst.getConfig().addEntityData(new EntityDataClearMobGoals());
            }
            if (npc != Advent2025Npc.CAT) {
                inst.getConfig().addEntityBehavior(new EntityLookAtPlayerBehavior(1));
                inst.getConfig().addEntityBehavior(new EntityRevertBehavior(2));
            }
            inst.getConfig().protectFromDamage();
            inst.getConfig().addEntityTrigger(
                new EntityTrigger() {
                    @Override
                    public void onPlayerClick(EntityInstance inst, Player player) {
                        onPlayerClickAdventNpc(npc, inst, player);
                    }
                }
            );
            npc.setInstance(inst);
        }
        Advent2025Npc.SANTA_CLAUSE.getInstance().getConfig().addEntityData(new EntityDataScale(1.25));
        Advent2025Npc.CHICKEN.getInstance().getConfig().addEntityData(new EntityDataScale(1.5));
        Advent2025Npc.TREE_FROG.getInstance().getConfig().addEntityData(new EntityDataScale(3));
        Advent2025Npc.CAT.getInstance().getConfig().addEntityData(new EntityDataScale(3));
        Advent2025Npc.CAT.getInstance().getConfig().addEntityData(new EntityDataCat(Cat.Type.BLACK, DyeColor.RED, false, true));
        for (Advent2025Npc npc : Advent2025Npc.values()) {
            plugin.getEntities().enableEntityInstance(npc.getInstance());
        }
        for (Advent2025Quest quest : Advent2025Quest.values()) {
            plugin.getQuests().enableQuest(quest.getOrCreateQuestInstance());
        }
        new AdventListener(this).enable();
        if (ADVENT_SERVER.isThisServer()) {
            Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
        }
    }

    public int getChristmasDay() {
        final Instant instant = Instant.now();
        final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC-11"));
        final LocalDate localDate = localDateTime.toLocalDate();
        final int year = localDate.getYear();
        final int month = localDate.getMonth().getValue();
        final int day = localDate.getDayOfMonth();
        if (year > 2025) return 25;
        if (year == 2025 && month == 12) {
            return Math.min(day, 25);
        }
        return 0;
    }

    private void tick() {
        final int day = getChristmasDay();
        for (String worldName : ADVENT_WORLDS) {
            final World world = Bukkit.getWorld(worldName);
            if (world == null) continue;
            for (Player player : world.getPlayers()) {
                tickPlayer(player, day);
            }
        }
    }

    private void tickPlayer(Player player, final int day) {
        final Session session = Session.of(player);
        if (!session.isEnabled()) return;
        if (!session.getActiveQuests().isEmpty()) return;
        for (Advent2025Quest quest : Advent2025Quest.values()) {
            if (quest.getDay() > day) return;
            if (session.hasCompletedQuest(quest.getInstance())) continue;
            if (!quest.getInstance().getAdventWorldName().equals(player.getWorld().getName())) return;
            session.startQuest(quest.getInstance());
            return;
        }
    }

    private void onPlayerClickAdventNpc(Advent2025Npc npc, EntityInstance inst, Player player) {
        if (!Session.isEnabled(player)) return;
        if (plugin.getScripts().getScriptOfPlayer(player) != null) return;
        final ScriptConfig scriptConfig = new ScriptConfig();
        List<String> dialog = null;
        Runnable completionAction = null;
        for (PlayerQuest playerQuest : Session.of(player).getActiveQuests()) {
            if (playerQuest.getQuest() instanceof AdventQuest adventQuest) {
                final AdventNpcDialog theDialog = adventQuest.getDialog(playerQuest, npc);
                if (theDialog != null) {
                    dialog = theDialog.dialog();
                    completionAction = theDialog.completionAction();
                }
            }
        }
        if (dialog == null) dialog = npc.getDefaultDialog();
        for (int i = 0; i < dialog.size(); i += 1) {
            final String line = dialog.get(i);
            final SpeechBubbleConfig speechBubbleConfig = SpeechBubbleConfig.ofMiniMessage(line);
            if (i == dialog.size() - 1) {
                speechBubbleConfig.setFinalUserPrompt();
            }
            scriptConfig.addEntry(
                new ScriptConfig.SpeakEntry(
                    true,
                    speechBubbleConfig,
                    inst.getSpeaker()
                )
            );
        }
        if (completionAction != null) {
            scriptConfig.addEntry(new ScriptConfig.RunnableEntry(completionAction));
        }
        final Script script = new Script(
            scriptConfig,
            Viewership.single(player)
        );
        plugin.getScripts().enableScript(script);
    }
}
