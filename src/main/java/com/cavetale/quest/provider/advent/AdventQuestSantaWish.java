package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.config.SpeechBubbleConfig.UserChoice;
import com.cavetale.quest.session.PlayerQuest;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.RegistryBuilderFactory;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.DialogRegistryEntry;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput.MultilineOptions;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.event.ClickCallback;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class AdventQuestSantaWish extends AdventQuest {
    @Override
    public void startPlayerQuest(PlayerQuest playerQuest) { }

    @Override
    public void enablePlayerQuest(PlayerQuest playerQuest) { }

    @Override
    public void disablePlayerQuest(PlayerQuest playerQuest) { }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, BossBar bossBar) {
        bossBar.name(textOfChildren(text("Talk to "), Advent2025Npc.SANTA_CLAUSE.getDisplayName()));
        bossBar.progress(1f);
    }

    @Override
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc) {
        if (npc != Advent2025Npc.SANTA_CLAUSE) return null;
        return new AdventNpcDialog(
            List.of(
                "Now it's time for you to make a wish. Think about what you would like to happen for you or anyone.",
                "Are you ready?"
            ),
            () -> { },
            List.of(
                new UserChoice("no", text("No")),
                new UserChoice("yes", text("Yes"))
            )
        );
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest) {
        return Vec3i.of(
            (int) Math.floor(Advent2025Npc.SANTA_CLAUSE.getX()),
            (int) Math.floor(Advent2025Npc.SANTA_CLAUSE.getY()),
            (int) Math.floor(Advent2025Npc.SANTA_CLAUSE.getZ())
        );
    }

    @Override
    public void onConfirmChoice(PlayerQuest playerQuest, String label) {
        if (!"yes".equals(label)) return;
        playerQuest.getSession().getPlayer().showDialog(Dialog.create(factory -> dialog(playerQuest, factory)));
    }

    private void dialog(PlayerQuest playerQuest, RegistryBuilderFactory<Dialog, ? extends DialogRegistryEntry.Builder> factory) {
        factory.empty()
            .base(
                DialogBase.builder(text("Test", GREEN))
                .body(List.of(DialogBody.plainMessage(text("Make a wish for yourself, your loved ones, or the community.", GRAY))))
                .inputs(
                    List.of(
                        DialogInput.text(
                            "wish", // key
                            text("") // label
                        )
                        .initial("")
                        .labelVisible(false)
                        .maxLength(4096)
                        .multiline(MultilineOptions.create(4, null))
                        .build()
                    )
                )
                .build()
            )
            .type(
                DialogType.confirmation(
                    ActionButton.builder(text("Send"))
                    .action(
                        DialogAction.customClick(
                            (response, audience) -> accept(playerQuest, response),
                            ClickCallback.Options.builder()
                            .lifetime(Duration.ofMinutes(10))
                            .uses(1)
                            .build()
                        )
                    )
                    .build(),
                    ActionButton.builder(text("Cancel", DARK_GRAY)).build()
                )
            );
    }

    private void accept(PlayerQuest playerQuest, DialogResponseView response) {
        if (playerQuest.isDisabled()) return;
        final String wish = response.getText("wish");
        if (wish == null || wish.isEmpty()) return;
        playerQuest.getSession().storeMemory("advent:wish", wish, Duration.ofDays(90));
        playerQuest.completeQuest();
        playerQuest.moveToFinishedQuests();
        playerQuest.getSession().getPlayer().sendMessage(
            textOfChildren(
                newline(),
                Advent2025Npc.SANTA_CLAUSE.getDisplayName(),
                text(": ", GRAY),
                text("Thanks for making a wish. I hope it comes true for you!"),
                newline()
            )
        );
    }
}
