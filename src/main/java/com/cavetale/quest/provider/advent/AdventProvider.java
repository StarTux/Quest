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
import com.cavetale.quest.entity.data.EntityDataScale;
import com.cavetale.quest.entity.data.EntityProfileData;
import com.cavetale.quest.script.Script;
import com.cavetale.quest.script.viewer.GlobalViewer;
import com.cavetale.quest.script.viewer.SingleViewer;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public final class AdventProvider {
    public static final NetworkServer ADVENT_SERVER = NetworkServer.CREATIVE;
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
                GlobalViewer.INSTANCE
            );
            inst.getConfig().setDisplayName(npc.getDisplayName());
            if (npc.getEntityType() == EntityType.MANNEQUIN) {
                inst.getConfig().addEntityData(new EntityProfileData().setTexture(npc.getTexture()));
            } else {
                final EntityDataAttributes data = new EntityDataAttributes();
                data.addAttribute(Attribute.MOVEMENT_SPEED, 0);
                inst.getConfig().addEntityData(data);
            }
            inst.getConfig().addEntityBehavior(new EntityLookAtPlayerBehavior().setPriority(1));
            inst.getConfig().addEntityBehavior(new EntityRevertBehavior().setPriority(2));
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
        for (Advent2025Npc npc : Advent2025Npc.values()) {
            plugin.getEntities().enableEntityInstance(npc.getInstance());
        }
        for (Advent2025Quest quest : Advent2025Quest.values()) {
            plugin.getQuests().enableQuest(quest.getOrCreateQuestInstance());
        }
        new AdventListener(this).enable();
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
        for (String line : dialog) {
            scriptConfig.addEntry(
                new ScriptConfig.SpeakEntry(
                    true,
                    SpeechBubbleConfig.ofMiniMessage(line),
                    inst.getSpeaker()
                )
            );
        }
        if (completionAction != null) {
            scriptConfig.addEntry(new ScriptConfig.RunnableEntry(completionAction));
        }
        final Script script = new Script(
            scriptConfig,
            SingleViewer.of(player)
        );
        plugin.getScripts().enableScript(script);
    }
}
