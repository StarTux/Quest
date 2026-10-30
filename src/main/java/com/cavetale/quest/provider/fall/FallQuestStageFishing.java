package com.cavetale.quest.provider.fall;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import com.cavetale.quest.session.PlayerQuest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
@RequiredArgsConstructor
public final class FallQuestStageFishing extends FallQuestStage {
    private final Component bossBarName = text("Fish the Wedding Band", GOLD);
    private final Vec3i goal = Vec3i.vec3i(273, 65, 327);

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return goal;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    public void onPlayerFish(PlayerQuest playerQuest, Progress progress, PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        final Player player = event.getPlayer();
        if (!FallProvider.WORLD.equals(player.getWorld().getName())) return;
        progress.addInteger("progress", 1);
        if (progress.getInteger("progress") < 3) {
            player.playSound(player, Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
            return;
        }
        final Gui gui = new Gui();
        final int currentStageIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
        gui.size(1 * 9);
        gui.layer(GuiOverlay.BLANK, GOLD);
        gui.title(text("The Farmer's Wedding Band!", WHITE, BOLD));
        gui.setItem(
            4,
            Mytems.WEDDING_RING.createIcon(List.of(text("I♡U", RED))),
            null
        );
        gui.onClose(evt -> getParent().advanceProgress(playerQuest, currentStageIndex));
        gui.open(event.getPlayer());
        player.playSound(player, Sound.ITEM_BOTTLE_FILL, SoundCategory.MASTER, 1f, 1f);
    }
}
