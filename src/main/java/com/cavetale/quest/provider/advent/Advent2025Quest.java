package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public enum Advent2025Quest {
    FLOUR(
        0,
        () -> new AdventQuestCollectItems(
            List.of(
                Vec3i.of(207, 67, 92),
                Vec3i.of(160, 66, 140),
                Vec3i.of(163, 67, 196),
                Vec3i.of(266, 67, 175),
                Vec3i.of(356, 67, 202),
                Vec3i.of(473, 74, 224),
                Vec3i.of(430, 69, 371),
                Vec3i.of(360, 66, 304),
                Vec3i.of(320, 66, 273),
                Vec3i.of(239, 74, 353),
                Vec3i.of(141, 67, 339),
                Vec3i.of(100, 68, 308),
                Vec3i.of(156, 67, 254),
                Vec3i.of(265, 66, 223)
            ),
            "Flour",
            new ItemStack(Material.SUGAR),
            Advent2025Npc.MILLER,
            Advent2025Npc.MILLER,
            List.of(
                "Please help! The flour I was supposed to deliver to the Farmer was caught by the wind.",
                "The flour has been blown all over town. Can you pick it up and bring it to me?"
            ),
            List.of("Thank you so much! I was seriously worried we couldn't finish the deliciouis Christmas cookies this year.")
        )
    ),
    MILK(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.DAIRY_FARMER,
                Advent2025Npc.MOO_COW,
                Advent2025Npc.DAIRY_FARMER
            ),
            List.of(
                List.of(
                    "Oh my, I have run out of milk!",
                    "And today of all days. We need that to turn it into butter.",
                    "Hmmm...",
                    "I wonder if the <blue>Moo Cow</blue> would be available...",
                    "I can't leave here. Would you mind asking her? Tell her it's super urgent."
                ),
                List.of(
                    "Moo!",
                    "Moo, mooo?",
                    "Moo moo moo, moo moo mooo?!",
                    "Moooooooo <emoji:heart>",
                    "<emoji:milk_bucket>"
                ),
                List.of(
                    "Why thank you! That is a huge load of milk you got there.",
                    "She wasn't uhm... upset, was she?",
                    "I hope you told her thank you from me.",
                    "Well, many thanks, partner! Talk to you later."
                )
            )
        )
    ),
    BUTTER(
        0,
        () -> new AdventQuestCollectItems(
            List.of(
                Vec3i.of(85, 78, 256),
                Vec3i.of(135, 69, 249),
                Vec3i.of(204, 67, 289),
                Vec3i.of(310, 68, 220),
                Vec3i.of(377, 66, 238),
                Vec3i.of(271, 67, 176),
                Vec3i.of(146, 66, 141)
            ),
            "Butter",
            new ItemStack(Material.GOLD_INGOT),
            Advent2025Npc.DAIRY_FARMER,
            Advent2025Npc.DAIRY_FARMER,
            List.of(
                "Well, well well. That's just swell!",
                "Just now that I have finally made all the butter, it got away from me.",
                "All my butter rolled downhill and got scattered all across town...",
                "Could I bother you with another request, friend?",
                "Would you mind collecting all that butter and bringing it back to me?"
            ),
            List.of(
                "Well gee, thank you!",
                "Now I can finally fulfil the baker's order.",
                "Christmas could not be saved without you, partner!"
            )
        )
    ),
    CHICKEN(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.POULTRY_FARMER,
                Advent2025Npc.CHICKEN,
                Advent2025Npc.POULTRY_FARMER
            ),
            List.of(
                List.of(
                    "Well what do you know, I forgot to check the chicken for eggs.",
                    "Unfortunately, I can't leave right now.",
                    "Could you go check if it has laid some?"
                ),
                List.of(
                    "Bawk bawk?",
                    "Bawk bawk bawk!",
                    "BAAAAWK! <emoji:uskull>"
                ),
                List.of(
                    "Hmm no? No eggs? Did she say why?",
                    "Aww well. We have to circle back tomorrow, I guess.",
                    "Thank you for your help anyway!",
                    "Who would have thought chickens were so complicated..."
                )
            )
        )
    ),
    EGGS(
        0,
        () -> new AdventQuestCollectItems(
            List.of(
                Vec3i.of(468, 72, 410),
                Vec3i.of(470, 72, 426),
                Vec3i.of(450, 71, 422),
                Vec3i.of(438, 69, 424),
                Vec3i.of(442, 69, 397),
                Vec3i.of(443, 71, 377),
                Vec3i.of(459, 72, 378),
                Vec3i.of(394, 68, 384),
                Vec3i.of(400, 68, 390),
                Vec3i.of(407, 66, 383),
                Vec3i.of(389, 67, 400),
                Vec3i.of(376, 69, 397)
            ),
            "Eggs",
            new ItemStack(Material.BROWN_EGG),
            Advent2025Npc.POULTRY_FARMER,
            Advent2025Npc.POULTRY_FARMER,
            List.of(
                "That's it, today's the day! No more excuses. That chicken ows us all some eggs.",
                "Would you mind going there and pick them all up?"
            ),
            List.of(
                "Finally! I knew the chicken would see reason eventually.",
                "And you did all the hard work, talking sense into the good girl.",
                "Thank you so much! We are now one step closer to delicious Christmas cookies."
            )
        )
    ),
    SUGAR(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.BAKER,
                Advent2025Npc.POSTMAN,
                Advent2025Npc.WIZARD,
                Advent2025Npc.BAKER
            ),
            List.of(
                List.of(
                    "I have flour, butter, eggs. I don't have sugar.",
                    "I need sugar to make Christmas cookies.",
                    "You like cookies? Ask the postman if they have arrived.",
                    "Please, friend. Help me make cookies."
                ),
                List.of(
                    "Another delivery... one more package. Always busy in the cold season. And it lasts all year around here..",
                    "Uh what? Sugar, you say? For the baker? Hmmm...",
                    "No, there was no sugar delivery. I mean there was, but that was for the wizard. Or so he said.",
                    "You have to ask him if you want that sugar back."
                ),
                List.of(
                    "Hello there. Came to see one of my awesome spells? Worry not, I have plenty more to show.",
                    "Huh, you want my sugar? But I, well... you see, I need some to put in my tea.",
                    "For the baker to make Christmas cookies, you say? Well, that's a different story, then. We all loves his cookies.",
                    "Here you go. Tell him I want the ones with the sprinkles."
                ),
                List.of(
                    "Sprinkles? Okay.",
                    "Thanks, friend! You're a good friend. Now we can make Christmas cookies. Yum!"
                )
            )
        )
    ),
    COOKIES(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.BAKER,
                Advent2025Npc.MILLER,
                Advent2025Npc.DAIRY_FARMER,
                Advent2025Npc.POULTRY_FARMER,
                Advent2025Npc.POSTMAN,
                Advent2025Npc.WIZARD,
                Advent2025Npc.SANTA_CLAUSE
            ),
            List.of(
                List.of("Cookies are done. Thank you. Bring them to all friends who helped, please. Least I can do."),
                List.of("Why thank you! These cookies look great, and they smell even better. <emoji:cookie>"),
                List.of("Thank you! I'll dip these in some fresh milk, that will make for an excellent treat. <emoji:cookie>"),
                List.of("Now these are some delectable looking baked goods. Have my thanks friend! <emoji:cookie>"),
                List.of("A little pick me up, for me? Thank you, now I can work twice as fast. <emoji:cookie>"),
                List.of("Awesome, sprinkles! You're the best. Tell the baker my thanks. <emoji:cookie>"),
                List.of("Ho ho ho! You brought Santa some delicious looking cookies. Thanks to you, there are now cookies for everyone. That's the Christmas spirit!")
            )
        )
    ),
    ;

    private final int adventWorldIndex;
    private final Supplier<AdventQuest> questSupplier;
    @Setter private AdventQuest instance;

    public int getDay() {
        return ordinal() + 1;
    }

    public String getQuestId() {
        return "advent:quest_2025_" + String.format("%02d", ordinal() + 1) + "_" + name().toLowerCase();
    }

    public AdventQuest getOrCreateQuestInstance() {
        if (instance == null) {
            instance = questSupplier.get();
            instance.setQuestEnum(this);
        }
        return instance;
    }
}
