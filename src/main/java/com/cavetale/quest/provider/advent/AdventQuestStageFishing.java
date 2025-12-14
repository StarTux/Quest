package com.cavetale.quest.provider.advent;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.util.Gui;
import com.cavetale.quest.session.PlayerQuest;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static io.papermc.paper.datacomponent.item.WrittenBookContent.writtenBookContent;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStageFishing extends AdventQuestStage {
    private final Component bossBarName;
    private final Vec3i goal;

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return goal;
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    public void onPlayerFish(PlayerQuest playerQuest, PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        final Player player = event.getPlayer();
        if (!getParent().getAdventWorldName().equals(player.getWorld().getName())) return;
        final Gui gui = new Gui();
        final int currentStageIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
        gui.size(1 * 9);
        gui.layer(GuiOverlay.BLANK, BLUE);
        gui.title(text("A Message in a Bottle!", WHITE, BOLD));
        gui.setItem(
            4,
            tooltip(
                new ItemStack(Material.EXPERIENCE_BOTTLE),
                List.of(
                    text("A Message in a Bottle!", WHITE, BOLD),
                    text("Click to open...", GRAY)
                )
            ),
            click -> {
                if (!click.isLeftClick()) return;
                player.playSound(player, Sound.ITEM_BOTTLE_FILL, SoundCategory.MASTER, 1f, 1f);
                final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
                book.setData(
                    DataComponentTypes.WRITTEN_BOOK_CONTENT,
                    writtenBookContent("Quest", "Cavetale")
                    .addPages(
                        List.of(
                            join(
                                separator(newline()),
                                text("Meet me by the train tracks at sunset."),
                                text(""),
                                text("  -Lila", BLUE, ITALIC)
                            )
                        )
                    )
                );
                player.closeInventory();
                player.openBook(book);
                getParent().advanceProgress(playerQuest, currentStageIndex);
            }
        );
        gui.open(event.getPlayer());
        player.playSound(player, Sound.ITEM_BOTTLE_FILL, SoundCategory.MASTER, 1f, 1f);
    }
}
