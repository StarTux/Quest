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
import static net.kyori.adventure.text.format.TextColor.color;
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
    BLACKSMITH(
        1, EntityType.MANNEQUIN, text("Blacksmith", DARK_RED, BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg0ZWNmNmYwYzIzZWQ1MmJlYTJjM2IzZWJmZGFjNDhiMGZjMTUyMmE3Mzc2YTkwMDBjY2UwNDc1OWVjMjE3NyJ9fX0=",
        145.50, 69.00, 214.50, 0f,
        List.of(
            "Hi there! Got anything for me to hammer? I can never get enough of it.",
            "Too bad I'm on vacation right now..."
        )
    ),
    REINDEER_HERDER(
        1, EntityType.MANNEQUIN, text("Reindeer Herder", color(0xa52a2a), BOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkzNzc1OWM1NTdmN2FmZjMzNzFmNWY4ZGZhM2U4NTY2YjM4OTk2ZGFmNDM0Nzk1M2IwYmJmNzFlNzZjIn19fQ==",
        312.5, 79.0, 127.5, -180f,
        List.of(
            "Well, dash my antlers! You're here to visit our town, aren't ya?",
            "I tend to the young reindeer here. Aren't they sweet?"
        )
    ),
    SNOW_FOX(
        1, EntityType.FOX, text("Snow Fox", AQUA),
        "",
        339.5, 67.0, 210.5, -45f,
        List.of(
            "Don't tell anyone you saw me. I'm playing hide and seek with the Herder."
        )
    ),
    LILA(
        1, EntityType.MANNEQUIN, text("Lila", color(0x800080)),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JjOGVmODk2NGZhNjI3ZGE3YmU3YjYyZjQ1YmFmOTE5ODBjNzBiYmRmNTQwOTQ4NTliNGNjZjNmYjc4NDUxZCJ9fX0=",
        246.50, 74.00, 390.50, 90.52f,
        List.of(
            "Yes, I'm back in town. I can't wait to visit mom."
        )
    ),
    JAKE(
        1, EntityType.MANNEQUIN, text("Jake", color(0xffff00)),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmYmNiNmFjMzM1YTEyOTZhY2NlYzZhM2VjOWE4MzE0NGRkODk1NWQ3M2I2YWMwNmY0NDI5YzZkYmJmMTc3OCJ9fX0=",
        222.5, 66.0, 245.0, -90f,
        List.of("Heya, buddy! Hope you have a nice Christmas.")
    ),
    LIBRARIAN(
        1, EntityType.MANNEQUIN, text("Librarian", BLUE),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY1MzQ4ZTdmMDRjZjBhODJhM2QxNGRjNTFhOWI4Y2QwZjI2N2JkNTM3YjhlNzBiMjU3N2RiY2Y5YjJjMTk1MyJ9fX0=",
        108.0, 68.5, 120.5, 0f,
        List.of("Greetings! Read any good books lately?")
    ),
    BLACK_BUNNY(
        1, EntityType.RABBIT, text("Black Bunny", DARK_GRAY),
        "",
        246.0, 67.0, 222.5, 0f,
        List.of("I'm always so busy around Christmas.")
    ),
    BROWN_BUNNY(
        1, EntityType.RABBIT, text("Brown Bunny", color(0xc2b280)),
        "",
        134.5, 67.0, 352.5, -98.53f,
        List.of("I feel like an old hare sometimes...")
    ),
    GOLD_BUNNY(
        1, EntityType.RABBIT, text("Gold Bunny", GOLD),
        "",
        202.5, 67.0, 173.5, 0f,
        List.of("I betcha nobody's fur is as soft and pretty as mine!")
    ),
    SNOWMAN(
        1, EntityType.SNOW_GOLEM, text("Snowman", AQUA),
        "",
        292.95, 68.0, 206.9, -14.39f,
        List.of("Always dress warm for the season. You don't want to catch a cold.")
    ),
    INVENTOR(
        1, EntityType.MANNEQUIN, text("Inventor", BLUE),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY1OGM1YWM2NGNkZDcxODllOGRiNTAzOTNmMjc0MzlmYzljZTkxNzdjOWM1Mjg1ZDZmMWY3YjRlZGE5ZDdlYyJ9fX0=",
        198.5, 68.0, 349.5, 90f,
        List.of("Oh hi there! Got any cool new toys for Christmas? I make those, you know.")
    )
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
