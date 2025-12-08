package com.cavetale.quest.provider.advent;

import com.cavetale.quest.entity.EntityInstance;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
@RequiredArgsConstructor
public enum Advent2025Npc {
    SANTA_CLAUSE(
        1, EntityType.MANNEQUIN, text("Santa Clause", RED, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVhMTkxNjczZTFjODkxMjI5ZmMxODM4OTllNjdlZDQwOWM0ZmYyMmZkZTcxMmRkNmQ1YmU0YTRmNzJlNCJ9fX0=",
        265.5, 67.0, 221.0, 0f,
        List.of(
            "Ho ho ho! Merry Christmas!",
            "Check out this awesome Christmas build. It was made in 2021 by the good people of Cavetale.",
            "We all had a lot of fun building this place together, like every year.",
            "Care to join us next time?"
        )
    ),
    MILLER(
        1, EntityType.MANNEQUIN, text("Miller", GOLD, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZlZmMyNDM3ZjQ1ZGIxN2M1NmI3ZmIxMzU3MTU2NGZhMThkZDA0ZGFhMjY4NmNiYmFiOGYyOWU3NjQ1ZiJ9fX0=",
        206.5, 76.0, 65.5, -135f,
        List.of(
            "People need their grains ground.",
            "That's where I come in.",
            "It ain't much but it's honest work.",
            "Yep.",
            "It bores me, sometimes.",
            "A lot actually.",
            "But somebody's gotta do it.",
            "Enough of this. I've got work to do."
        )
    ),
    DAIRY_FARMER(
        1, EntityType.MANNEQUIN, text("Dairy Farmer", BLUE, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjVkYTViMmU4ZTRlMmQ3ZjgxZWQ4NTM1MTBmODQ4YTJlZTZhNjQ1N2E5ZjI4YmIwNzFiMTE5ODBkZTkxODUifX19",
        65.5, 82.0, 247.5, -90f,
        List.of(
            "Hello there, neighbor.",
            "When did you move to the Winter Woods?",
            "Around here, it's Christmas all year round!"
        )
    ),
    MOO_COW(
        1, EntityType.COW, text("Moo Cow", BLUE),
        "",
        405.9, 66.0, 387.2, 0f,
        List.of(
            "Moo!",
            "Mooooo!",
            "MooOooOOooOoOOo!"
        )
    ),
    POULTRY_FARMER(
        1, EntityType.MANNEQUIN, text("Poultry Farmer", BLUE, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I0M2IyMzE4OWRjZjEzMjZkYTQyNTNkMWQ3NTgyZWY1YWQyOWY2YzI3YjE3MWZlYjE3ZTMxZDA4NGUzYTdkIn19fQ==",
        75.5, 81.0, 271.5, 180f,
        List.of(
            "Howdy, neighbor.",
            "Make sure to eat enough protein.",
            "And avoid too much sweet stuff."
        )
    ),
    CHICKEN(
        1, EntityType.CHICKEN, text("Cluck Cluck Chicken", RED),
        "",
        465.5, 71.0, 397.5, 90f,
        List.of("Bawk bawk?!")
    ),
    BAKER(
        1, EntityType.MANNEQUIN, text("Baker", LIGHT_PURPLE, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhkZWE2MjJmYjBmYmJkMjYzOTM4NGMxNjA0ZjVmMWEzYzExMWFhOTkzNDQ3MzE5ZmJhYzQ5NGJmZjQ0NzcifX19",
        332.5, 68.0, 219.5, -90f,
        List.of(
            "You like Christmas candy?",
            "Good, me too! I also make them. Bring me ingredients, I can make anything. And it's scrumptious!"
        )
    ),
    POSTMAN(
        1, EntityType.MANNEQUIN, text("Postman", YELLOW, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmIwNDMyZmFiNzIxNDBjN2ZkMmVhMWU4YmFlMWVhOGJmOTkwYzkzNjkzNzAxY2I3NzFlYTI5YTU5NDZiNGIxMiJ9fX0=",
        83.5, 80.0, 359.5, 0f,
        List.of(
            "So much mail to handle this time of year. I'm losing my mind over here.",
            "Just kidding, it's also a lot of fun! <emoji:smile>"
        )
    ),
    WIZARD(
        1, EntityType.MANNEQUIN, text("Wizard", DARK_PURPLE, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZmOWUxZjY0ZjJiYWZlNzIxZmFkYTVmNjdlOWUyNWJiNGQxMTEyNWQ2Nzk1ZGE2ZTg0OGVlMzgyOGU4NSJ9fX0=",
        174.5, 100.0, 356.5, -180f,
        List.of(
            "So much to see from up here. Most people don't realize, my magic makes the snowfall happen.",
            "Don't believe me? Watch this:",
            "Χιόνα καλέω, νιφάδες ἐξ οὐρανοῦ, κρύσταλλον πνοὴν ἐκ νεφέλης βαθείας. Ἐλθέ, χεῖμα, σὺν ἀνέμοις σιωπῇ, γῆν καλύψαι λευκῇ σινδόνι θεῶν. Ὑπὸ σελάνας φέγγος, ἐγὼ ἐπικαλούμην, νίψον τὸν κόσμον ἐν παγῇ σῇ.",
            "Impressive, huh?"
        )
    ),
    TREE_FROG(
        1, EntityType.FROG, text("Tree Frog", GREEN),
        "",
        184.32, 67.00, 68.78, -55.26f,
        List.of(
            "Ribbit!"
        )
    ),
    CAT(
        1, EntityType.CAT, text("Mistletoe", DARK_GRAY, BOLD),
        "",
        143.00, 67.00, 340.00, 180f,
        List.of(
            "Meow meow meow...",
            "Purrr..."
        )
    ),
    ;

    private final int worldNumber;
    private final EntityType entityType;
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

    public record Stage(Advent2025Npc npc, List<String> dialog) { }

    public Stage stage(String... dialog) {
        return new Stage(this, List.of(dialog));
    }
}
