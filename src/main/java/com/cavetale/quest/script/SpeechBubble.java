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
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.quest.QuestPlugin.questPlugin;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

/**
 * Speech Bubbles are temporary entities which display their text for
 * a while, usually animated, then disappear again.
 */
@Data
public final class SpeechBubble {
    public static final int LETTERS_PER_LINE = 24;
    public static final int PIXELS_PER_LINE = 1600;
    private static final Component TAB = text("  ");
    private static final float TEXT_DISPLAY_SCALE = 0.75f;
    private static final Transformation TEXT_DISPLAY_TRANSFORMATION = new Transformation(
        new Vector3f(0f, 0f, 0f),
        new AxisAngle4f(0f, 0f, 0f, 0f),
        new Vector3f(TEXT_DISPLAY_SCALE, TEXT_DISPLAY_SCALE, TEXT_DISPLAY_SCALE),
        new AxisAngle4f(0f, 0f, 0f, 0f)
    );
    // Configuration
    private final SpeechBubbleConfig config;
    private final Speaker speaker;
    private final Viewership viewership;
    private Script parent;
    // Runtime
    private TextDisplay textDisplay;
    private List<List<Component>> lines;
    private int tickAge;
    private int revealedLetters;
    private int totalLetters;
    private static final String SPACES = " ".repeat(40);
    private static final String SPACES2 = " ".repeat(19);
    private boolean highSpeed;
    private boolean finished;
    private int finishedTicks;
    private boolean disabled;
    private boolean cancelled;
    private boolean blink;
    private int blinkTicks;
    private int userChoice;
    private int userChoiceLineCount;
    private boolean userConfirmed;

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
        if (config.getUserPrompt().isChoice()) {
            userChoiceLineCount = makeChoiceLines().size();
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
                e.setBackgroundColor(Color.fromARGB(0xb0_10_10_70));
                e.setLineWidth(PIXELS_PER_LINE);
                e.setSeeThrough(true); // through blocks
                e.setShadowed(true);
                e.text(makeCurrentText());
                e.setBillboard(TextDisplay.Billboard.CENTER);
                e.setBrightness(new TextDisplay.Brightness(15, 15));
                e.setTransformation(TEXT_DISPLAY_TRANSFORMATION);
                if (!viewership.isGlobal()) {
                    e.setVisibleByDefault(false);
                    for (Player player : viewership.getPlayers()) {
                        player.showEntity(questPlugin(), e);
                    }
                }
            });
        if (textDisplay == null) return;
        registerEntity(textDisplay);
    }

    public void tick() {
        if (!viewersStillThere()) {
            cancelled = true;
            if (parent != null) parent.cancel();
            disable();
            return;
        }
        if (finished && userConfirmed) {
            if (config.getUserPrompt().isChoice() && parent != null) {
                parent.onConfirmChoice(this, config.getChoices().get(userChoice).label());
            }
            disable();
            return;
        }
        tickAge += 1;
        final Location location = speaker.getSpeechBubbleLocation();
        if (location == null) {
            disable();
            return;
        }
        textDisplay.teleport(location);
        textDisplay.text(makeCurrentText());
        if (!finished) {
            revealedLetters += highSpeed ? 10 : 1;
            if (revealedLetters >= totalLetters) {
                finished = true;
            }
            viewership.playSound(location, Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 0.05f, 0.5f);
        } else {
            if (config.isAutomatic() && finishedTicks++ > 100) {
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
                    speaker.getDisplayName().decorate(UNDERLINED)
                )
            );
        }
        for (List<Component> line : lines) {
            List<Component> newLine = new ArrayList<>();
            for (Component letter : line) {
                if (lettersShown++ < revealedLetters) {
                    newLine.add(letter);
                }
            }
            if (newLine.isEmpty()) {
                newLines.add(TAB);
            } else {
                newLines.add(textOfChildren(TAB, join(noSeparators(), newLine)));
            }
        }
        if (finished && config.getUserPrompt().hasUserPrompt()) {
            if (blinkTicks++ > 5) {
                blink = !blink;
                blinkTicks = 0;
            }
            if (config.getUserPrompt().isChoice()) {
                newLines.addAll(makeChoiceLines());
                newLines.add(textOfChildren(TAB, text(tiny("use scroll wheel"), DARK_GRAY)));
            } else if (!blink) {
                if (config.getUserPrompt().isContinue()) {
                    newLines.add(textOfChildren(text(SPACES2), text("\u25bc", color(0xff0000), BOLD)));
                } else if (config.getUserPrompt().isFinal()) {
                    newLines.add(textOfChildren(text(SPACES2), text("\u25c6", color(0xff0000), BOLD)));
                }
            } else {
                newLines.add(text(SPACES));
            }
        } else {
            for (int i = 0; i < userChoiceLineCount; i += 1) {
                newLines.add(TAB);
            }
            newLines.add(text(SPACES));
        }
        return join(separator(newline()), newLines);
    }

    private List<Component> makeChoiceLines() {
        final List<Component> choiceLines = new ArrayList<>();
        final List<Component> choiceLine = new ArrayList<>();
        int choiceLineLength = 0;
        for (int i = 0; i < config.getChoices().size(); i += 1) {
            final SpeechBubbleConfig.UserChoice choice = config.getChoices().get(i);
            final Component display;
            if (userChoice == i) {
                display = !blink
                    ? textOfChildren(text("[", GRAY), choice.display().decorate(UNDERLINED), text("]", GRAY))
                    : textOfChildren(space(), choice.display().decorate(UNDERLINED), space());
            } else {
                display = textOfChildren(space(), choice.display().color(GRAY), space());
            }
            final int displayLength = Text.countCharacters(display);
            if (choiceLineLength + displayLength + 1 > LETTERS_PER_LINE) {
                choiceLines.add(textOfChildren(TAB, join(noSeparators(), choiceLine)));
                choiceLine.clear();
                choiceLine.add(display);
                choiceLineLength = displayLength;
            } else {
                if (!choiceLine.isEmpty()) {
                    choiceLine.add(space());
                    choiceLineLength += 1;
                }
                choiceLine.add(display);
                choiceLineLength += displayLength;
            }
        }
        if (!choiceLine.isEmpty()) {
            choiceLines.add(textOfChildren(TAB, join(noSeparators(), choiceLine)));
        }
        return choiceLines;
    }

    /**
     * Player clicked the speaker of this bubble.
     */
    public boolean onPlayerProgressDialog(Player player) {
        if (userConfirmed) {
            return false;
        } else if (!config.getUserPrompt().hasUserPrompt()) {
            return false;
        } else if (tickAge == 0) {
            return false;
        } else if (finished) {
            userConfirmed = true;
            return true;
        } else if (!highSpeed) {
            highSpeed = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean onPlayerChoice(Player player, boolean isRelative, int value) {
        if (userConfirmed || !finished || !config.getUserPrompt().isChoice()) return false;
        if (isRelative) {
            userChoice += value;
            if (userChoice < 0) {
                userChoice = config.getChoices().size() - 1;
            } else if (userChoice >= config.getChoices().size()) {
                userChoice = 0;
            }
        } else if (value < config.getChoices().size()) {
            userChoice = value;
        }
        return true;
    }

    private void registerEntity(Entity entity) {
        questPlugin().getScripts().registerEntity(entity, this);
    }

    private void unregisterEntity(Entity entity) {
        questPlugin().getScripts().unregisterEntity(entity);
    }

    private boolean viewersStillThere() {
        if (viewership.isGlobal()) return true;
        final Location location = speaker.getSpeechBubbleLocation();
        for (Player player : viewership.getPlayers()) {
            if (!player.getWorld().equals(location.getWorld())) continue;
            if (player.getLocation().distanceSquared(location) > 64) {
                continue;
            }
            return true;
        }
        return false;
    }
}
