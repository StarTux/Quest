package com.cavetale.quest;

import com.cavetale.core.command.AbstractCommand;
import org.bukkit.command.CommandSender;

public final class QuestCommand extends AbstractCommand<QuestPlugin> {
    public QuestCommand(final QuestPlugin plugin) {
        super(plugin, "quest");
    }

    @Override
    public void onEnable() {
        rootNode.addChild("info").denyTabCompletion()
            .description("Info Command")
            .senderCaller(this::info);
    }

    public boolean info(CommandSender sender, String[] args) {
        return true;
    }
}
