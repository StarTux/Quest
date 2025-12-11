package com.cavetale.quest.provider.advent;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.quest.util.Text.parseMiniMessage;

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
            List.of(
                Advent2025Npc.MILLER.stage(
                    "Please help! The flour I was supposed to deliver to the Farmer was caught by the wind.",
                    "The flour has been blown all over town. Can you pick it up and bring it to me?"
                )
            ),
            Advent2025Npc.MILLER,
            List.of(
                "Thank you so much! I was seriously worried we couldn't finish the deliciouis Christmas cookies this year."
            )
        )
    ),
    MILK(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.DAIRY_FARMER.stage(
                    "Oh my, I have run out of milk!",
                    "And today of all days. We need that to turn it into butter.",
                    "Hmmm...",
                    "I wonder if the <blue>Moo Cow</blue> would be available...",
                    "I can't leave here. Would you mind asking her? Tell her it's super urgent."
                ),
                Advent2025Npc.MOO_COW.stage(
                    "Moo!",
                    "Moo, mooo?",
                    "Moo moo moo, moo moo mooo?!",
                    "Moooooooo <emoji:heart>",
                    "<emoji:milk_bucket>"
                ),
                Advent2025Npc.DAIRY_FARMER.stage(
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
            List.of(
                Advent2025Npc.DAIRY_FARMER.stage(
                    "Well, well well. That's just swell!",
                    "Just now that I have finally made all the butter, it got away from me.",
                    "All my butter rolled downhill and got scattered all across town...",
                    "Could I bother you with another request, friend?",
                    "Would you mind collecting all that butter and bringing it back to me?"
                )
            ),
            Advent2025Npc.DAIRY_FARMER,
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
                Advent2025Npc.POULTRY_FARMER.stage(
                    "Well what do you know, I forgot to check the chicken for eggs.",
                    "Unfortunately, I can't leave right now.",
                    "Could you go check if it has laid some?"
                ),
                Advent2025Npc.CHICKEN.stage(
                    "Bawk bawk?",
                    "Bawk bawk bawk!",
                    "BAAAAWK! <emoji:uskull>"
                ),
                Advent2025Npc.POULTRY_FARMER.stage(
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
            List.of(
                Advent2025Npc.POULTRY_FARMER.stage(
                    "That's it, today's the day! No more excuses. That chicken ows us all some eggs.",
                    "Would you mind going there and pick them all up?"
                )
            ),
            Advent2025Npc.POULTRY_FARMER,
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
                Advent2025Npc.BAKER.stage(
                    "I have flour, butter, eggs. I don't have sugar.",
                    "I need sugar to make Christmas cookies.",
                    "You like cookies? Ask the postman if they have arrived.",
                    "Please, friend. Help me make cookies."
                ),
                Advent2025Npc.POSTMAN.stage(
                    "Another delivery... one more package. Always busy in the cold season. And it lasts all year around here..",
                    "Uh what? Sugar, you say? For the baker? Hmmm...",
                    "No, there was no sugar delivery. I mean there was, but that was for the wizard. Or so she said.",
                    "You have to ask him if you want that sugar back."
                ),
                Advent2025Npc.WIZARD.stage(
                    "Hello there. Came to see one of my awesome spells? Worry not, I have plenty more to show.",
                    "Huh, you want my sugar? But I, well... you see, I need some to put in my tea.",
                    "For the baker to make Christmas cookies, you say? Well, that's a different story, then. We all love his cookies.",
                    "Here you go. Tell him I want the ones with the sprinkles."
                ),
                Advent2025Npc.BAKER.stage(
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
                Advent2025Npc.BAKER.stage(
                    "Cookies are done. Thank you. Bring them to all friends who helped, please. Least I can do."
                ),
                Advent2025Npc.MILLER.stage(
                    "Why thank you! These cookies look great, and they smell even better. <emoji:cookie>"
                ),
                Advent2025Npc.DAIRY_FARMER.stage(
                    "Thank you! I'll dip these in some fresh milk, that will make for an excellent treat. <emoji:cookie>"
                ),
                Advent2025Npc.POULTRY_FARMER.stage(
                    "Now these are some delectable looking baked goods. Have my thanks friend! <emoji:cookie>"
                ),
                Advent2025Npc.POSTMAN.stage(
                    "A little pick me up, for me? Thank you, now I can work twice as fast. <emoji:cookie>"
                ),
                Advent2025Npc.WIZARD.stage(
                    "Awesome, sprinkles! You're the best. Tell the baker my thanks. <emoji:cookie>"
                ),
                Advent2025Npc.SANTA_CLAUSE.stage(
                    "Ho ho ho! You brought Santa some delicious looking cookies. Thanks to you, there are now cookies for everyone. That's the Christmas spirit!"
                )
            )
        )
    ),
    TINSEL(
        0,
        () -> new AdventQuestTalkChain(
            List.of(
                Advent2025Npc.SANTA_CLAUSE.stage(
                    "Ho ho ho! I've got a problem. All the tinsel for the Christmas tree is missing!",
                    "I heard the <yellow>Postman</yellow> might know where it went. Could you ask him?"
                ),
                Advent2025Npc.POSTMAN.stage(
                    "Tinsel? Oh, I saw some shiny stuff earlier, but I was in a hurry.",
                    "The <blue>Dairy Farmer</blue> mentioned something about it. You should ask him!"
                ),
                Advent2025Npc.DAIRY_FARMER.stage(
                    "Tinsel? Oh dear, I thought that was just glittery hay!",
                    "The <blue>Moo Cow</blue> was nibbling on something sparkly earlier. Maybe she knows where it is."
                ),
                Advent2025Npc.MOO_COW.stage(
                    "Moo! <emoji:heart> Moo moo moo… <emoji:nether_star>"
                    + "\n<gray><italic>Translation: I saw the frog playing with it near the pond!"
                ),
                Advent2025Npc.TREE_FROG.stage(
                    "Ribbit ribbit! <emoji:cold_frog_face> Ribbit ribbit ribbit!"
                    + "\n<gray><italic>Translation: Oh, this? I was just admiring how shiny it is! Here you go!",
                    "<italic>Drops the tinsel"
                ),
                Advent2025Npc.SANTA_CLAUSE.stage(
                    "Ah, perfect! Now we can decorate the tree.",
                    "Thanks to you, the town will sparkle this Christmas!"
                )
            )
        )
    ),
    ORNAMENTS(
        0,
        () -> new AdventQuestCollectItems(
            List.of(
                Vec3i.of(137, 72, 339),
                Vec3i.of(130, 73, 333),
                Vec3i.of(143, 67, 327),
                Vec3i.of(154, 70, 330),
                Vec3i.of(156, 68, 340)
            ),
            "Ornaments",
            Mytems.PURPLE_CHRISTMAS_BALL.createItemStack(),
            List.of(
                Advent2025Npc.SANTA_CLAUSE.stage(
                    "Ho ho ho! The shiny ornaments for the tree are missing! I heard the <light_purple>Baker</light_purple> saw something suspicious earlier."
                ),
                Advent2025Npc.BAKER.stage(
                    "Ornaments? Yes, I saw something sparkly. Too busy baking cookies, though.",
                    "The <blue>Poultry Farmer</blue> might know. He complained about something shiny. Something rolling into his coop..."
                ),
                Advent2025Npc.POULTRY_FARMER.stage(
                    "Ornaments? Why yes, indeed!",
                    "The <red>Cluck Cluck Chicken</red> was pecking at them earlier. She might have dragged them toward the barn."
                ),
                Advent2025Npc.CHICKEN.stage(
                    "Bawk! Bawk bawk bawk! Cluck cluck!"
                    + "\n<gray><italic>Translation: I had the ornaments, until <gray>Mistletoe the Cat</gray> took them away from me."
                ),
                Advent2025Npc.CAT.stage(
                    "Meow! Purrrr... Meow meow meow?"
                    + "\n<gray><italic>Translation: Oh, you came for my toys! Why do you need them?",
                    "Purr purr hiss, meow!"
                    + "\n<gray><italic>Translation: Santa needs them? Why didn't you say so? Go pick them up, quickly."
                )
            ),
            Advent2025Npc.SANTA_CLAUSE,
            List.of(
                "Ah, thank you! Mistletoe is always causing a ruckus, but she's part of the family.",
                "Now the tree is going to shine bright! We may just save Christmas."
            )
        )
    ),
    SLEIGH(
        0,
        () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to <red>Santa Clause"),
                Advent2025Npc.SANTA_CLAUSE,
                List.of(
                    "Ho ho ho! I'm awaiting an important delivery, but it's getting late, and I haven't heard from them.",
                    "Would you mind checking with the <yellow>Postman</yellow> if everything is in order?"
                )
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <yellow>Postman</yellow> about Deliveries"),
                Advent2025Npc.POSTMAN,
                List.of(
                    "Oh no! Santa's sleigh won't fly! It's just sitting there like a lump of wood.",
                    "Could you take a look at it for me? I'm swamped with deliveries.",
                    "And while you're there, could you pick up the presents that are still on the sleigh? They need to be delivered to the townsfolk.",
                    "I'd do it myself, but I can't leave my post right now."
                )
            ),
            new AdventQuestStageCollectItems(
                parseMiniMessage("See about <red>Santa's Sleigh</red>"),
                List.of(
                    Vec3i.of(61, 81, 382), // Entrance
                    Vec3i.of(61, 82, 371), // On top of sleigh
                    Vec3i.of(63, 80, 362), // Corner
                    Vec3i.of(61, 80, 370), // Below sleigh
                    Vec3i.of(60, 87, 369) // Under ceiling
                ),
                (i, vec) -> Mytems.CHRISTMAS_TOKEN.createItemStack()
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Report back to the <yellow>Postman"),
                Advent2025Npc.POSTMAN,
                List.of(
                    "Thank you! The townsfolk will be so happy.",
                    "You know, if the sleigh isn't flying, maybe the <dark_red>Blacksmith</dark_red> could take a look at it. He's great with anything mechanical."
                )
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <dark_red>Blacksmith"),
                Advent2025Npc.BLACKSMITH,
                List.of(
                    "The runners are fine, and the harness is intact. But what about the magic bell? It's missing!",
                    "I wonder where it went. Santa's Sleigh cannot launch off the ground without it.",
                    "Better ask him who was last seen riding the sleigh. Whoever it was must know about the whereabouts of the bell."
                )
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask <dark_red>Santa</dark_red> about the Sleigh"),
                Advent2025Npc.SANTA_CLAUSE,
                List.of(
                    "Ho ho ho, my friend.",
                    "Now it's getting late and the mystery of the Sleigh sounds like an adventure for another day..."
                )
            )
        )
    ),
    BELL(
        0,
        () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Continue with <red>Santa Clause"),
                Advent2025Npc.SANTA_CLAUSE,
                "Ho ho ho! The sleigh still won't fly! We need to find that magic bell!",
                "The <dark_red>Blacksmith</dark_red> said you should ask who was last seen riding the sleigh. I think that was the <light_purple>Wizard</light_purple>!"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Ah, the magic bell! I borrowed it to test a new spell: <light_purple>The Chime of Truth</light_purple>.",
                "But I left it with the Poultry Farmer for safekeeping. He was worried about the <red>chicken</red> getting into mischief again."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <blue>Poultry Farmer"),
                Advent2025Npc.POULTRY_FARMER,
                "Oh dear, I completely forgot! I put the bell in the barn to keep it safe, but now it's gone.",
                "That <red>chicken</red> must have taken it again. She's been acting out lately, ever since I've been too busy to check on her.",
                "I really should spend more time with her. She's usually so well-behaved when I do."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Check the Barn"),
                Advent2025Npc.CHICKEN,
                "Fine, Santa can have his Magic Bell.",
                "But only if you promise to tell the Poultry Farmer to visit more often.",
                "<italic><gray>The chicken gives you the <emoji:bell><gold>Magic Bell</gold> with its beak"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <blue>Poultry Farmer</blue> for a promise"),
                Advent2025Npc.POULTRY_FARMER,
                "You found the bell, that's awesome!",
                "What did you say? The <red>chicken</red>? More time?",
                "Yes, absolutely. I'll make sure to visit her more often. A happy chicken is a well-behaved chicken, after all. Far less likely to nick shiny objects."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return the <emoji:bell><gold>Magic Bell</gold> to <red>Santa"),
                Advent2025Npc.SANTA_CLAUSE,
                "Ho ho ho! Now that's what I call teamwork!",
                "This bell always brings people together, and sometimes chickens.",
                "Also, it makes the sleigh fly. Yep. Good thing we found it before it was too late!"
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
