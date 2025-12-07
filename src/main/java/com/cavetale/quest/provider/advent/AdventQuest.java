package com.cavetale.quest.provider.advent;

import com.cavetale.core.money.Money;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import com.cavetale.quest.Quest;
import com.cavetale.quest.session.PlayerQuest;
import lombok.Data;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import static com.cavetale.quest.QuestPlugin.questPlugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;
import static net.kyori.adventure.title.Title.title;

@Data
public abstract class AdventQuest implements Quest {
    private Advent2025Quest questEnum;

    @Override
    public final String getQuestId() {
        return questEnum.getQuestId();
    }

    public final int getDay() {
        return questEnum.getDay();
    }

    public final int getAdventWorldIndex() {
        return questEnum.getAdventWorldIndex();
    }

    public final String getAdventWorldName() {
        return AdventProvider.ADVENT_WORLDS.get(getAdventWorldIndex());
    }

    public abstract void makeBossBar(PlayerQuest playerQuest, BossBar bossBar);

    public abstract AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc);

    public void onPlayerMoveBlock(PlayerQuest playerQuest, Vec3i vector) { }

    public abstract Vec3i getNextGoal(PlayerQuest playerQuest);

    @Override
    public final void onFirstCompletion(PlayerQuest playerQuest) {
        final Player player = playerQuest.getSession().getPlayer();
        player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.2f, 1.5f);
        player.showTitle(
            title(
                textOfChildren(Mytems.CHRISTMAS_TOKEN, text("Day " + questEnum.getDay(), RED, BOLD)),
                text("Complete", GREEN)
            )
        );
        player.getInventory().addItem(Mytems.KITTY_COIN.createItemStack());
        Money.get().give(player.getUniqueId(), 1000L * (long) questEnum.getDay(), questPlugin(), "Advent Calendar");
    }
}
