package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;

@Getter
@RequiredArgsConstructor
public final class AdventQuestStaged extends AdventQuest {
    private final List<AdventQuestStage> stages;

    public AdventQuestStaged(final AdventQuestStage... stages) {
        this.stages = List.of(stages);
    }

    @Override
    public void enable() {
        for (AdventQuestStage stage : stages) {
            stage.setParent(this);
        }
        stages.forEach(AdventQuestStage::enable);
    }

    @Override
    public void startPlayerQuest(PlayerQuest playerQuest) {
        Progress progress = new Progress();
        playerQuest.setCustomData(progress);
        playerQuest.setTag(progress);
        stages.get(0).enable(playerQuest, progress.getCurrentStageProgress());
    }

    @Override
    public void enablePlayerQuest(PlayerQuest playerQuest) {
        Progress progress = playerQuest.getSavedTag(Progress.class, Progress::new);
        playerQuest.setCustomData(progress);
        getCurrentStage(progress).enable(playerQuest, progress.getCurrentStageProgress());
    }

    @Override
    public void disablePlayerQuest(PlayerQuest playerQuest) {
        final Progress progress = getProgress(playerQuest);
        getCurrentStage(progress).disable(playerQuest, progress.getCurrentStageProgress());
    }

    @Override
    public void tickPlayerQuest(PlayerQuest playerQuest) {
        final Progress progress = getProgress(playerQuest);
        getCurrentStage(progress).tick(playerQuest, progress.getCurrentStageProgress());
    }

    public AdventQuestStage getCurrentStage(PlayerQuest playerQuest) {
        final Progress progress = getProgress(playerQuest);
        return getCurrentStage(progress);
    }

    public AdventQuestStage getCurrentStage(Progress progress) {
        return stages.get(progress.currentStageIndex);
    }

    public Progress getProgress(PlayerQuest playerQuest) {
        return playerQuest.getCustomData(Progress.class, Progress::new);
    }

    public void saveProgress(PlayerQuest playerQuest) {
        playerQuest.setTag(getProgress(playerQuest));
    }

    /**
     * The current Stage will call this whenever its goal is
     * reached. Supply the currentStageIndex in case it might get
     * called later in a function.
     */
    public void advanceProgress(PlayerQuest playerQuest, final int currentStageIndex) {
        if (playerQuest.isDisabled()) return;
        final Progress progress = getProgress(playerQuest);
        if (progress.currentStageIndex != currentStageIndex) return;
        advanceProgress(playerQuest, progress);
    }

    /**
     * The current Stage will call this whenever its goal is
     * reached.
     */
    public void advanceProgress(PlayerQuest playerQuest) {
        if (playerQuest.isDisabled()) return;
        final Progress progress = getProgress(playerQuest);
        advanceProgress(playerQuest, progress);
    }

    private void advanceProgress(PlayerQuest playerQuest, Progress progress) {
        final AdventQuestStage currentStage = getCurrentStage(progress);
        currentStage.disable(playerQuest, progress.getCurrentStageProgress());
        if (currentStage.getNext() != null) {
            // Jump
            for (int i = 0; i < stages.size(); i += 1) {
                if (currentStage.getNext().equals(stages.get(i).getLabel())) {
                    progress.currentStageIndex = i;
                    getCurrentStage(progress).enable(playerQuest, progress.getCurrentStageProgress());
                    playerQuest.setTag(progress);
                    return;
                }
            }
        }
        if (progress.currentStageIndex < stages.size() - 1) {
            progress.currentStageIndex += 1;
            getCurrentStage(progress).enable(playerQuest, progress.getCurrentStageProgress());
            playerQuest.setTag(progress);
        } else {
            playerQuest.setTag(progress);
            playerQuest.completeQuest();
        }
    }

    @Override
    public Vec3i getNextGoal(PlayerQuest playerQuest) {
        final Progress progress = getProgress(playerQuest);
        return getCurrentStage(progress).getNextGoal(playerQuest, progress.getCurrentStageProgress());
    }

    @Override
    public void makeBossBar(PlayerQuest playerQuest, BossBar bossBar) {
        final Progress progress = getProgress(playerQuest);
        getCurrentStage(progress).makeBossBar(playerQuest, progress.getCurrentStageProgress(), bossBar);
    }

    @Override
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Advent2025Npc npc) {
        final Progress progress = getProgress(playerQuest);
        final AdventNpcDialog currentDialog = getCurrentStage(progress).getDialog(playerQuest, progress.getCurrentStageProgress(), npc);
        if (currentDialog != null) {
            return currentDialog;
        }
        for (int i = progress.currentStageIndex - 1; i >= 0; i -= 1) {
            final AdventNpcDialog oldDialog = stages.get(i).getDialog(playerQuest, progress.getStageProgress(1), npc);
            if (oldDialog != null) {
                // Return the dialog without the callback.
                return new AdventNpcDialog(oldDialog.dialog(), () -> { }, null);
            }
        }
        return null;
    }

    @Override
    public void onConfirmChoice(PlayerQuest playerQuest, String label) {
        final Progress progress = getProgress(playerQuest);
        for (int i = 0; i < stages.size(); i += 1) {
            // Jump to label
            if (label.equals(stages.get(i).getLabel())) {
                getCurrentStage(progress).disable(playerQuest, progress.getCurrentStageProgress());
                progress.currentStageIndex = i;
                getCurrentStage(progress).enable(playerQuest, progress.getCurrentStageProgress());
                playerQuest.setTag(progress);
                return;
            }
        }
    }

    @Data
    public static final class Progress implements Serializable {
        private int currentStageIndex = 0;
        private Map<Integer, AdventQuestStage.Progress> stages = new HashMap<>();

        public AdventQuestStage.Progress getStageProgress(int index) {
            return stages.computeIfAbsent(index, i -> new AdventQuestStage.Progress());
        }

        public AdventQuestStage.Progress getCurrentStageProgress() {
            return getStageProgress(currentStageIndex);
        }
    }
}
