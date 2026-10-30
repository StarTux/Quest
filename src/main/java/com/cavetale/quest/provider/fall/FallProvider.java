package com.cavetale.quest.provider.fall;

import com.cavetale.core.connect.NetworkServer;
import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.config.ScriptConfig;
import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.script.Script;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
public final class FallProvider {
    public static final NetworkServer SERVER = NetworkServer.HUB;
    public static final String WORLD = "halloween2026";

    @Getter
    private static FallProvider instance;

    public void enable() {
        if (!SERVER.isThisServer()) return;
        instance = this;
        for (FallCharacter character : FallCharacter.values()) {
            character.enable();
        }
        for (FallQuestList questList : FallQuestList.values()) {
            questList.initialize();
            questList.buildContent();
            questList.enable();
        }
        new FallListener().enable();
        Bukkit.getScheduler().runTaskTimer(QuestPlugin.questPlugin(), this::tick, 1L, 1L);
    }

    public void disable() {
    }

    public void onPlayerClick(Player player, FallCharacter character) {
        if (!Session.isEnabled(player)) return;
        if (QuestPlugin.questPlugin().getScripts().getScriptOfPlayer(player) != null) return;
        final ScriptConfig scriptConfig = new ScriptConfig();
        List<String> dialog = null;
        for (PlayerQuest playerQuest : Session.of(player).getActiveQuests()) {
            if (playerQuest.getQuest() instanceof FallQuest fallQuest) {
                final List<String> theDialog = fallQuest.getDialog(playerQuest, character);
                if (theDialog != null) {
                    dialog = theDialog;
                }
            }
        }
        if (dialog == null) {
            dialog = List.of("Happy Halloween!");
        }
        for (String text : dialog) {
            final SpeechBubbleConfig speechBubbleConfig = SpeechBubbleConfig.ofMiniMessage(text);
            speechBubbleConfig.setFinalUserPrompt();
            scriptConfig.addEntry(
                new ScriptConfig.SpeakEntry(
                    true,
                    speechBubbleConfig,
                    character.getInstance().getSpeaker()
                )
            );
        }
        scriptConfig.addEntry(
            new ScriptConfig.RunnableEntry(() -> onFinishDialog(player, character)));
        final Script script = new Script(
            scriptConfig,
            Viewership.single(player)
        );
        QuestPlugin.questPlugin().getScripts().enableScript(script);
    }

    public void onFinishDialog(Player player, FallCharacter character) {
        for (PlayerQuest playerQuest : Session.of(player).getActiveQuests()) {
            if (playerQuest.getQuest() instanceof FallQuest fallQuest && fallQuest.getCurrentStage(playerQuest) instanceof FallQuestStageTalk stageTalk) {
                stageTalk.onFinishDialog(playerQuest, fallQuest.getProgress(playerQuest).getCurrentStageProgress(), character);
            }
        }

    }

    private void tick() {
        final World world = Bukkit.getWorld(WORLD);
        if (world == null) return;
        for (Player player : world.getPlayers()) {
            tickPlayer(player);
        }
    }

    private void tickPlayer(Player player) {
        final Session session = Session.of(player);
        if (!session.isEnabled()) return;
        if (!session.getActiveQuests(FallQuest.class).isEmpty()) return;
        for (FallQuestList questList : FallQuestList.values()) {
            if (!session.hasCompletedQuest(questList.getInstance())) {
                session.startQuest(questList.getInstance());
                return;
            }
        }
    }
}
