package com.cavetale.quest;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.script.SpeechBubble;
import com.cavetale.quest.script.speaker.FixedSpeaker;
import com.cavetale.quest.script.viewer.GlobalViewer;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public final class QuestAdminCommand extends AbstractCommand<QuestPlugin> {
    public QuestAdminCommand(final QuestPlugin plugin) {
        super(plugin, "questadmin");
    }

    @Override
    public void onEnable() {
        rootNode.addChild("info").denyTabCompletion()
            .description("Info Command")
            .senderCaller(this::info);
        rootNode.addChild("start").arguments("<player> <quest>")
            .description("Start player quest")
            .completers(
                CommandArgCompleter.NULL,
                CommandArgCompleter.supplyList(() -> List.copyOf(plugin.getQuests().getAllQuestIds()))
            )
            .senderCaller(this::start);
        rootNode.addChild("cancel").arguments("<player> <quest>")
            .description("Cancel player quest")
            .completers(
                CommandArgCompleter.NULL,
                CommandArgCompleter.supplyList(() -> List.copyOf(plugin.getQuests().getAllQuestIds()))
            )
            .senderCaller(this::cancel);
    }

    public boolean info(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length == 0) return false;
        final SpeechBubbleConfig config = new SpeechBubbleConfig();
        config.setMiniMessage(String.join(" ", args));
        final SpeechBubble bubble = new SpeechBubble(
            config,
            new FixedSpeaker(player.getEyeLocation(), player.displayName()),
            GlobalViewer.INSTANCE
        );
        plugin.getScripts().enableSpeechBubble(bubble);
        return true;
    }

    public boolean start(CommandSender sender, String[] args) {
        if (args.length != 2) return false;
        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            throw new CommandWarn("Player not found: " + args[0]);
        }
        final Quest quest = plugin.getQuests().getQuest(args[1]);
        if (quest == null) {
            throw new CommandWarn("Quest not found: " + args[1]);
        }
        if (!Session.isEnabled(target)) {
            throw new CommandWarn("Session not ready: " + target.getName());
        }
        sender.sendMessage(text("Starting quest " + quest.getQuestId() + " for player " + target.getName() + "...", YELLOW));
        Session.of(target).startQuest(quest);
        return true;
    }


    public boolean cancel(CommandSender sender, String[] args) {
        if (args.length != 2) return false;
        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            throw new CommandWarn("Player not found: " + args[0]);
        }
        final Quest quest = plugin.getQuests().getQuest(args[1]);
        if (quest == null) {
            throw new CommandWarn("Quest not found: " + args[1]);
        }
        if (!Session.isEnabled(target)) {
            throw new CommandWarn("Session not ready: " + target.getName());
        }
        for (PlayerQuest playerQuest : Session.of(target).getActiveQuests()) {
            if (playerQuest.getQuest() != quest) continue;
            sender.sendMessage(text("Cancelling quest " + quest.getQuestId() + " for player " + target.getName() + "...", YELLOW));
            playerQuest.cancelQuest();
            return true;
        }
        throw new CommandWarn(target.getName() + " does not have quest " + quest.getQuestId());
    }
}
