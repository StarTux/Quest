package com.cavetale.quest;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.script.SpeechBubble;
import com.cavetale.quest.script.speaker.FixedSpeaker;
import com.cavetale.quest.script.viewer.GlobalViewer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class QuestAdminCommand extends AbstractCommand<QuestPlugin> {
    public QuestAdminCommand(final QuestPlugin plugin) {
        super(plugin, "questadmin");
    }

    @Override
    public void onEnable() {
        rootNode.addChild("info").denyTabCompletion()
            .description("Info Command")
            .senderCaller(this::info);
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
}
