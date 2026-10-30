package com.cavetale.quest.provider.fall;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * A stage can be created easily, with a simple public constructor.
 *
 * It is intended that the entier AdventQuestStaged is constructed in
 * one statement.
 * The AdventQuestStaged will will in the parent.
 */
public abstract class FallQuestStage {
    @Getter @Setter
    private FallQuest parent;

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
    public List<String> getDialog(PlayerQuest playerQuest, Progress progress, FallCharacter character) {
        return null;
    }

    /**
     * The progress class has to be final because it will be stored
     * anonmyously in a map in FallQuest.Progress.
     */
    @Data
    public static final class Progress implements Serializable {
        private Map<String, Integer> integers;
        private List<Vec3i> vec3iList;

        public Map<String, Integer> getIntegers() {
            if (integers == null) {
                integers = new HashMap<>();
            }
            return integers;
        }

        public List<Vec3i> getVec3iList() {
            if (vec3iList == null) {
                vec3iList = new ArrayList<>();
            }
            return vec3iList;
        }

        public int getInteger(String key) {
            return getIntegers().getOrDefault(key, 0);
        }

        public void addInteger(String key, int value) {
            getIntegers().put(key, getInteger(key) + value);
        }
    }

    public final FallQuestStage label(final String value) {
        this.label = value;
        return this;
    }

    public final FallQuestStage next(final String value) {
        this.next = value;
        return this;
    }

    public void onClickBlock(PlayerQuest playerQuest, Progress progress, PlayerInteractEvent event) { }
}
