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
                    result.add(space());
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

    public static List<List<Component>> splitLines(List<Component> letters, int maxLineLength) {
        final List<List<Component>> result = new ArrayList<>();
        final List<Component> line = new ArrayList<>();
        final List<Component> word = new ArrayList<>();
        for (Component letter : letters) {
            if (space().equals(letter)) {
                splitLinesAddWord(result, line, word, maxLineLength);
            } else if (newline().equals(letter)) {
                splitLinesAddWord(result, line, word, maxLineLength);
                if (!line.isEmpty()) {
                    result.add(List.copyOf(line));
                    line.clear();
                }
            } else {
                word.add(letter);
            }
        }
        splitLinesAddWord(result, line, word, maxLineLength);
        if (!line.isEmpty()) result.add(List.copyOf(line));
        return result;
    }

    private static void splitLinesAddWord(List<List<Component>> result, List<Component> line, List<Component> word, int maxLineLength) {
        if (word.isEmpty()) {
            return;
        } else if (line.isEmpty() && word.size() >= maxLineLength) {
            // Word gets its own line.
            result.add(List.copyOf(word));
        } else if (line.size() + 1 + word.size() > maxLineLength) {
            // Add line, then word.
            result.add(List.copyOf(line));
            line.clear();
            splitLinesAddWord(result, line, word, maxLineLength);
        } else {
            if (!line.isEmpty()) line.add(space());
            line.addAll(List.copyOf(word));
            word.clear();
        }
    }
}
