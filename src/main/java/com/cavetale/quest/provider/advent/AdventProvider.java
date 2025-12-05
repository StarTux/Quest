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
import com.cavetale.quest.entity.data.EntityDataScale;
import com.cavetale.quest.entity.data.EntityProfileData;
import com.cavetale.quest.script.Script;
import com.cavetale.quest.script.viewer.SingleViewer;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public final class AdventProvider {
    public static final NetworkServer ADVENT_SERVER = NetworkServer.CREATIVE;
    public static final List<String> ADVENT_WORLDS = List.of(
        "advent_2025_01"
    );
    private final QuestPlugin plugin;

    public void enable() {
        for (Advent2025Npc npc : Advent2025Npc.values()) {
            final EntityInstance inst = new EntityInstance(
                new EntityConfig(EntityType.MANNEQUIN),
                new SpawnLocationConfig(
                    ADVENT_SERVER,
                    ADVENT_WORLDS.get(npc.getWorldNumber() - 1),
                    npc.getX(), npc.getY(), npc.getZ(),
                    npc.getYaw(), 0f
                )
            );
            inst.getConfig().setDisplayName(npc.getDisplayName());
            inst.getConfig().addEntityData(new EntityProfileData().setTexture(npc.getTexture()));
            inst.getConfig().addEntityBehavior(new EntityLookAtPlayerBehavior().setPriority(1));
            inst.getConfig().addEntityBehavior(new EntityRevertBehavior().setPriority(2));
            inst.getConfig().protectFromDamage();
            inst.getConfig().addEntityTrigger(
                new EntityTrigger() {
                    @Override
                    public void onPlayerClick(EntityInstance inst, Player player) {
                        onPlayerClickAdventNpc(inst, player);
                    }
                }
            );
            npc.setInstance(inst);
        }
        Advent2025Npc.SANTA_CLAUSE.getInstance().getConfig().addEntityData(new EntityDataScale(1.25));
        for (Advent2025Npc npc : Advent2025Npc.values()) {
            plugin.getEntities().enableEntityInstance(npc.getInstance());
        }
        plugin.getQuests().enableQuest(new AdventQuest2025Day01());
    }

    private void onPlayerClickAdventNpc(EntityInstance inst, Player player) {
        if (plugin.getScripts().getScriptOfPlayer(player) != null) return;
        final ScriptConfig scriptConfig = new ScriptConfig();
        scriptConfig.addEntry(
            new ScriptConfig.SpeakEntry(
                true,
                SpeechBubbleConfig.ofMiniMessage("<red><bold>Merry Christmas!!!"),
                inst.getSpeaker()
            )
        );
        scriptConfig.addEntry(
            new ScriptConfig.SpeakEntry(
                true,
                SpeechBubbleConfig.ofMiniMessage("<red>Have <green>you <red>been <red>nice <green>this <red>year?! Or have you been naughty? Don't lie to me now, I can always tell. Hahahah."),
                inst.getSpeaker()
            )
        );
        final Script script = new Script(
            scriptConfig,
            SingleViewer.of(player)
        );
        plugin.getScripts().enableScript(script);
        player.sendMessage("HEIGHT " + inst.getSpawnedEntity().getHeight());
    }
}
