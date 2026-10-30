package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@Getter
@RequiredArgsConstructor
public final class FallQuestStageTalk extends FallQuestStage {
    private final FallCharacter character;
    private final Component bossBarName;
    private final List<String> dialog;

    public FallQuestStageTalk(final FallCharacter character, final String bossBarText, final List<String> dialog) {
        this(character, Component.text(bossBarText, NamedTextColor.GOLD), dialog);
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress) {
        return Vec3i.of(
            (int) Math.floor(character.getX()),
            (int) Math.floor(character.getY()),
            (int) Math.floor(character.getZ())
        );
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar) {
        bossBar.name(bossBarName);
        bossBar.progress(1f);
    }

    @Override
    public List<String> getDialog(PlayerQuest playerQuest, Progress progress, FallCharacter theCharacter) {
        if (character != theCharacter) return null;
        return dialog;
    }

    public void onFinishDialog(PlayerQuest playerQuest, Progress progress, FallCharacter theCharacter) {
        if (character != theCharacter) return;
        final int oldIndex = getParent().getProgress(playerQuest).getCurrentStageIndex();
        getParent().advanceProgress(playerQuest, oldIndex);
    }
}
