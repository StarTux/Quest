package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;

/**
 * A stage can be created easily, with a simple public constructor.
 *
 * It is intended that the entier AdventQuestStaged is constructed in
 * one statement.
 * The AdventQuestStaged will will in the parent.
 */
public abstract class AdventQuestStage {
    @Getter @Setter
    private AdventQuestStaged parent;

    @Getter
    private String label;

    @Getter
    private String next;

    public void enable() { }

    public void disable() { }

    public void enable(PlayerQuest playerQuest, Progress progress) { }

    public void disable(PlayerQuest playerQuest, Progress progress) { }

    public void tick(PlayerQuest playerQuest, Progress progress) { }

    public abstract Vec3i getNextGoal(PlayerQuest playerQuest, Progress progress);

    public abstract void makeBossBar(PlayerQuest playerQuest, Progress progress, BossBar bossBar);

    /**
     * Override in order to provide NPC dialog.
     */
    public AdventNpcDialog getDialog(PlayerQuest playerQuest, Progress progress, Advent2025Npc npc) {
        return null;
    }

    /**
     * The progress class has to be final because it will be stored
     * anonmyously in a map in AdventQuestStaged.Progress.
     */
    @Data
    public static final class Progress implements Serializable {
        private Map<String, Integer> integers;

        public Map<String, Integer> getIntegers() {
            if (integers == null) {
                integers = new HashMap<>();
            }
            return integers;
        }
    }

    public final AdventQuestStage label(final String value) {
        this.label = value;
        return this;
    }

    public final AdventQuestStage next(final String value) {
        this.next = value;
        return this;
    }
}
