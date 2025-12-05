package com.cavetale.quest.script;

import com.cavetale.quest.config.SpeechBubbleConfig;
import com.cavetale.quest.script.speaker.Speaker;
import com.cavetale.quest.script.viewer.Viewership;
import com.cavetale.quest.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import static com.cavetale.quest.QuestPlugin.questPlugin;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

/**
 * Speech Bubbles are temporary entities which display their text for
 * a while, usually animated, then disappear again.
 */
@Data
public final class SpeechBubble {
    public static final int LETTERS_PER_LINE = 26;
    public static final int PIXELS_PER_LINE = 1600;
    private static final Component TAB = text("  ");
    // Configuration
    private final SpeechBubbleConfig config;
    private final Speaker speaker;
    private final Viewership viewership;
    // Runtime
    private TextDisplay textDisplay;
    private List<List<Component>> lines;
    private int tickAge;
    private int totalLetters;
    private static final String SPACES = " ".repeat(40);
    private boolean finished;
    private boolean disabled;

    /**
     * This should only be called by Entities#enableSpeechBubble.
     */
    public void enable() {
        processConfig();
        spawn();
    }

    public void disable() {
        disabled = true;
        if (textDisplay != null) {
            unregisterEntity(textDisplay);
            textDisplay.remove();
            textDisplay = null;
        }
    }

    public void processConfig() {
        final Component message = config.getMessage();
        final List<Component> letters = Text.splitLetters(message);
        lines = Text.splitLines(letters, LETTERS_PER_LINE);
        totalLetters = 0;
        for (List<Component> line : lines) {
            totalLetters += line.size();
        }
    }

    public void spawn() {
        final Location location = speaker.getSpeechBubbleLocation();
        if (location == null) {
            disable();
        } else {
            spawn(location);
        }
    }

    public void spawn(Location location) {
        textDisplay = location.getWorld().spawn(location, TextDisplay.class, e -> {
                e.setPersistent(false);
                e.setAlignment(TextDisplay.TextAlignment.LEFT);
                e.setDefaultBackground(false);
                //e.setBackgroundColor(Color.fromARGB(0));
                e.setBackgroundColor(Color.fromARGB(0x40_10_10_70));
                e.setLineWidth(PIXELS_PER_LINE);
                e.setSeeThrough(true); // through blocks
                e.setShadowed(true);
                e.text(makeCurrentText());
                e.setBillboard(TextDisplay.Billboard.CENTER);
                e.setBrightness(new TextDisplay.Brightness(15, 15));
            });
        if (textDisplay == null) return;
        registerEntity(textDisplay);
    }

    public void tick() {
        tickAge += 1;
        final Location location = speaker.getSpeechBubbleLocation();
        if (location == null) {
            disable();
            return;
        }
        textDisplay.teleport(location);
        textDisplay.text(makeCurrentText());
        if (tickAge > totalLetters * 3) {
            finished = true;
            if (config.isDisableWhenFinished()) {
                disable();
            }
        }
    }

    public Component makeCurrentText() {
        int lettersShown = 0;
        final List<Component> newLines = new ArrayList<>();
        newLines.add(text(SPACES));
        if (speaker.shouldShowDisplayName()) {
            newLines.add(
                textOfChildren(
                    TAB,
                    text("[", GRAY),
                    speaker.getDisplayName(),
                    text("]", GRAY)
                )
            );
        }
        for (List<Component> line : lines) {
            List<Component> newLine = new ArrayList<>();
            for (Component letter : line) {
                if (lettersShown++ / 2 < tickAge) {
                    newLine.add(letter);
                }
            }
            if (newLine.isEmpty()) {
                newLines.add(TAB);
            } else {
                newLines.add(textOfChildren(TAB, join(noSeparators(), newLine)));
            }
        }
        newLines.add(text(SPACES));
        return join(separator(newline()), newLines);
    }

    private void registerEntity(Entity entity) {
        questPlugin().getScripts().registerEntity(entity, this);
    }

    private void unregisterEntity(Entity entity) {
        questPlugin().getScripts().unregisterEntity(entity);
    }
}
