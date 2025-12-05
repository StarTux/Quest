package com.cavetale.quest;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.dialog.SpeechBubble;
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
        final SpeechBubble bubble = new SpeechBubble();
        bubble.setConfig(config);
        bubble.processConfig();
        final SpawnLocationConfig spawnLocationConfig = new SpawnLocationConfig(player.getEyeLocation());
        spawnLocationConfig.clearPitchAndYaw();
        bubble.setSpawnLocationConfig(spawnLocationConfig);
        plugin.getDialogs().enableSpeechBubble(bubble);
        return true;
    }
}
