package com.cavetale.quest.provider.advent;

import com.cavetale.quest.entity.EntityInstance;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
@RequiredArgsConstructor
public enum Advent2025Npc {
    SANTA_CLAUSE(
        1, text("Santa Clause", RED, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVhMTkxNjczZTFjODkxMjI5ZmMxODM4OTllNjdlZDQwOWM0ZmYyMmZkZTcxMmRkNmQ1YmU0YTRmNzJlNCJ9fX0=",
        265.5, 67.0, 221.0, 0f,
        List.of(
            "<gray>Ho ho ho! Merry Christmas!",
            "<gray>Check out this awesome Christmas build. It was made in 2024 by the good people of Cavetale.",
            "<gray>We all had a lot of fun building this place together, like every year.",
            "<gray>Care to join us next time?"
        )
    ),
    MILLER(
        1, text("Miller", GOLD, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZlZmMyNDM3ZjQ1ZGIxN2M1NmI3ZmIxMzU3MTU2NGZhMThkZDA0ZGFhMjY4NmNiYmFiOGYyOWU3NjQ1ZiJ9fX0=",
        206.5, 76.0, 65.5, -135f,
        List.of(
            "<gray>People need their grains ground.",
            "<gray>That's where I come in.",
            "<gray>It ain't much but it's honest work.",
            "<gray>Yep.",
            "<gray>It bores me, sometimes.",
            "<gray>A lot actually.",
            "<gray>But somebody's gotta do it.",
            "<gray>Enough of this. I've got work to do."
        )
    ),
    ;

    private final int worldNumber;
    private final Component displayName;
    private final String texture;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final List<String> defaultDialog;
    @Setter
    private EntityInstance instance;

    public static Advent2025Npc ofInstance(EntityInstance instance) {
        for (var it : values()) {
            if (instance == it.instance) return it;
        }
        return null;
    }
}
