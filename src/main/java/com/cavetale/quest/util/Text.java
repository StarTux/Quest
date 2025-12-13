package com.cavetale.quest.util;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import static com.cavetale.quest.message.EmojiResolver.emojiResolver;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class Text {
    private Text() { }

    public static Component parseMiniMessage(String in) {
        return miniMessage().deserialize(in, emojiResolver());
    }

    public static List<Component> splitLetters(Component component) {
        return splitLettersHelper(component, new ArrayList<>(), Style.empty());
    }

    private static List<Component> splitLettersHelper(Component component, List<Component> result, Style style) {
        style = style.merge(component.style());
        if (component instanceof TextComponent textComponent) {
            String content = textComponent.content();
            for (int i = 0; i < content.length(); i += 1) {
                String letter = content.substring(i, i + 1);
                if (letter.equals(" ")) {
                    result.add(space().style(style));
                } else if (letter.equals("\n")) {
                    result.add(Component.newline());
                } else {
                    result.add(Component.text(letter, style));
                }
            }
        } else {
            result.add(component);
        }
        for (Component extra : component.children()) {
            splitLettersHelper(extra, result, style);
        }
        return result;
    }

    /**
     * Split the letters into lines.
     */
    public static List<List<Component>> splitLines(List<Component> letters, int maxLineLength) {
        // The list variables below are edited by this function and
        // the helper.
        final List<List<Component>> result = new ArrayList<>();
        // Line always holds the current line. The word variable is
        // constantly ready to be drained into the line.
        final List<Component> line = new ArrayList<>();
        // The `word` always contains one word, maybe including the
        // preceding space. This is because spaces need to retain
        // their style in case they are underlined or striked through.
        // The helper function knows to cut them off where needed.
        final List<Component> word = new ArrayList<>();
        for (Component letter : letters) {
            if (letter instanceof TextComponent text && " ".equals(text.content())) {
                splitLinesAddWordHelper(result, line, word, maxLineLength);
                word.add(letter);
            } else if (newline().equals(letter)) {
                splitLinesAddWordHelper(result, line, word, maxLineLength);
                if (!line.isEmpty()) {
                    result.add(List.copyOf(line));
                    line.clear();
                }
            } else {
                word.add(letter);
            }
        }
        splitLinesAddWordHelper(result, line, word, maxLineLength);
        if (!line.isEmpty()) result.add(List.copyOf(line));
        return result;
    }

    /**
     * This helper is called whenever a word is complete. It adds the
     * word to the line, and the line to the result if possible.
     */
    private static void splitLinesAddWordHelper(List<List<Component>> result, List<Component> line, List<Component> word, int maxLineLength) {
        if (word.isEmpty()) {
            return;
        } else if (line.isEmpty() && word.size() >= maxLineLength) {
            // Word gets its own line and is split if needed.
            if (word.get(0) instanceof TextComponent space && " ".equals(space.content())) {
                word.remove(0);
            }
            final List<Component> one = List.copyOf(word.subList(0, maxLineLength));
            final List<Component> two = List.copyOf(word.subList(maxLineLength, word.size()));
            result.add(List.copyOf(one));
            word.clear();
            word.addAll(two);
            splitLinesAddWordHelper(result, line, word, maxLineLength);
        } else if (line.size() + word.size() > maxLineLength) {
            // Word does not fit into line.
            // Add line, then call self.
            result.add(List.copyOf(line));
            line.clear();
            splitLinesAddWordHelper(result, line, word, maxLineLength);
        } else {
            // Word does fit into line.
            if (line.isEmpty() && word.get(0) instanceof TextComponent space && " ".equals(space.content())) {
                word.remove(0);
            }
            line.addAll(List.copyOf(word));
            word.clear();
        }
    }
}
