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
    STAR(
        0,
        () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to <red>Santa Clause"),
                Advent2025Npc.SANTA_CLAUSE,
                "Ho ho ho! The star for the top of the tree is missing!",
                "Without it, the tree won't shine bright enough to guide my sleigh on Christmas Eve.",
                "I heard the <light_purple>Wizard</light_purple> saw something suspicious last night. Could you ask her for me?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Ah, the star! I saw a strange glow coming from the <color:#a52a2a>Reindeer Herder's</color> house last night.",
                "But when I asked about it, he clammed up. Something's not right."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "Me? No, I don't know anything about a star.",
                "I've just been busy with the young reindeer. They need extra care in this cold.",
                "...",
                "Don't believe me? Just look around."
            ),
            new AdventQuestStageCollectItems(
                parseMiniMessage("Look around for the <emoji:star><gold>Star"),
                List.of(Vec3i.of(313, 83, 139)),
                (i, vec) -> Mytems.STAR.createItemStack()
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Confront the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "Alright, alright! I took it, but only because the little ones need light to grow strong.",
                "The nights are so dark and cold, and I was worried they wouldn't make it through the winter.",
                "But now they're doing better. Could you... could you ask the <light_purple>Wizard</light_purple> for help? Maybe she knows a safer way to keep them warm?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Aha! So the herder took it to help the reindeer, did he?",
                "Well I can't fault him for that. I'll hex a small light charm for the pen.",
                "Φῶς ἁπαλὸν, θερμὸν καὶ ἄφθαρτον, σῴζειν τὰς νεογνὰς ἐλάφους ἡμῶν.",
                "Here, take this. It's not as powerful as the star, but it'll keep them warm and safe."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Bring the spell to the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "Excellent, now the young reindeer can grow. Thank you, friend!",
                "And tell <red>Santa</red> I'm sorry for taking the star."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to <red>Santa Clause"),
                Advent2025Npc.SANTA_CLAUSE,
                "Ho ho ho! So the herder took it for the reindeer, did he?",
                "That's the spirit of Christmas: Looking out for others, even if it means bending the rules a little.",
                "Now the tree can shine bright, and the reindeer will be just fine. Thank you, friend!"
            )
        )
    ),
    FOX(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                List.of(
                    "Oh no! My snow fox is missing!",
                    "She's usually curled up near the reindeer pen, but she vanished last night.",
                    "Could you ask around? Maybe someone saw her.",
                    "Who do you think could have seen something?"
                )
            )
            .addUserChoice("start", "Dunno")
            .addUserChoice("postman", "Postman")
            .addUserChoice("wizard", "Wizard")
            .addUserChoice("miller", "Miller")
            .label("start"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <yellow>Postman"),
                Advent2025Npc.POSTMAN,
                "A snow fox? Can't say I've seen her.",
                "But if she's missing, maybe she chased after something shiny. They're curious like that."
            )
            .label("postman")
            .next("start"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <gold>Miller"),
                Advent2025Npc.MILLER,
                "A fox? Not around here.",
                "Though I did hear some rustling near the barn last night. Probably just the wind."
            )
            .label("miller")
            .next("start"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Of course I see things. I see everything from way up here, day or night.",
                "Ah, the snow fox! I saw her darting toward the old clock tower earlier.",
                "She seemed spooked by something, but I didn't want to interfere. Foxes are clever, they find their own hiding spots."
            )
            .label("wizard"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find the <aqua>Snow Fox"),
                Advent2025Npc.SNOW_FOX,
                "Eep, you found me. Now it's the Herder's turn to hide..."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "Oh my, she was hiding this whole time!?",
                "Boy, I better get fetch her before she catches a cold. Thank you so much!"
            )
        )
    ),
    LILA(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to <gray>Mistletoe the Cat"),
                Advent2025Npc.CAT,
                "Meow! Psst! over here!",
                "I saw something strange in the pond. It's been bobbing around all morning.",
                "Go grab your fishing rod and see if you can scoop it out of the water."
            ),
            new AdventQuestStageFishing(
                parseMiniMessage("Go Fishing by the Pond"),
                Vec3i.of(388, 67, 399)
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Meet <color:#800080>Lila"),
                Advent2025Npc.LILA,
                "I can't believe this resurfaced now.",
                "Mom still tells the story of how Dad and I used to play that game.",
                "Thanks for bringing it. It's like a little hello from the past."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to <gray>Mistletoe the Cat"),
                Advent2025Npc.CAT,
                "Told you it was worth fishing out."
            )
        )
    ),
    SCAVENGER_HUNT(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Ah, hello! I'm brewing a spell to make the Christmas tree glow brighter than ever.",
                "But I need four special ingredients from the creatures around town.",
                "A golden feather, a sleek whisker, a slippery lily pad, and a well-chewed bone.",
                "Do you think you could find these items for me?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find a <gold>Golden Feather"),
                Advent2025Npc.CHICKEN,
                "Bawk! My golden feather? It's my favorite!",
                "What do you say? The Wizard needs it for the Christmas Tree?",
                "Well... if you say it's for the tree, I suppose I can share."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find a <gray>Sleek Whisker"),
                Advent2025Npc.CAT,
                "A whisker? Do I look like a broom to you?",
                "Purr... the tree, huh? Meow...",
                "Fine. But only if you promise to tell the Wizard I'm the most elegant cat in town."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find a <green>Slippery Lily Pad"),
                Advent2025Npc.TREE_FROG,
                "Ribbit! My lily pad? But I need it to nap!",
                "Croak! Oh, for the big tree?!",
                "Alright, take it. But I want a new one by tomorrow."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Find a <color:#ffff00>Well Chewed Bone"),
                Advent2025Npc.JAKE,
                "My bone? I buried it! There's no way I'm digging it up again.",
                "Oh well, for Christmas, I suppose we can make an exception.",
                "Here you go! Merry Christmas, buddy."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "Perfect! Now the tree will shine with the spirit of the town.",
                "Thank you, friend. The magic of Christmas is in the little things, and the help of good neighbors."
            )
        )
    ),
    RECIPE_BOOK(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <light_purple>Baker"),
                Advent2025Npc.BAKER,
                "Ah, you're here! I could really use your help.",
                "I lost my grandmother's recipe book. The one with the gingerbread recipe. With her secret molasses trick.",
                "I was going to make it for the festival, but without it... well, it won't be the same.",
                "I think I last saw it near the <yellow>Postman</yellow>'s sorting table."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <yellow>Postman"),
                Advent2025Npc.POSTMAN,
                "A recipe book? Oh, that little stained thing?",
                "I thought it was trash, so I tossed it in the old barrel by the mill.",
                "Though now that I think about it, the <blue>Librarian</blue> was poking around there earlier."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <blue>Librarian"),
                Advent2025Npc.LIBRARIAN,
                "Oh! That recipe? I, uh, borrowed it.",
                "I wanted to preserve it, it's a piece of history!",
                "But I didn't ask first. I should have.",
                "Mind collecting the <gold>missing pages</gold>, please? They were loose and are now floating around the house."
            ),
            new AdventQuestStageCollectItems(
                parseMiniMessage("Find the <gold>Missing Pages"),
                List.of(
                    Vec3i.of(117, 68, 123),
                    Vec3i.of(124, 69, 141),
                    Vec3i.of(128, 70, 119),
                    Vec3i.of(123, 77, 131),
                    Vec3i.of(106, 77, 133),
                    Vec3i.of(102, 78, 139),
                    Vec3i.of(98, 77, 131)
                ),
                (i, vec) -> new ItemStack(Material.MAP)
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <blue>Librarian"),
                Advent2025Npc.LIBRARIAN,
                "You're right to give me that look.",
                "I've been so focused on saving old stories, I forgot they're still living for some people.",
                "Here. And... tell the Baker I'll help him write it down properly this time."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <light_purple>Baker"),
                Advent2025Npc.BAKER,
                "Thank you. And thank the Librarian for me.",
                "Maybe it's time I shared this recipe with the whole town.",
                "Grandma always said good memories are for passing on."
            )
        )
    ),
    BUNNIES(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "Oh, good, you're here! The bunnies are at it again.",
                "Black, Brown, and Gold are arguing over who gets the last patch of winter clover near the old oak.",
                "They won't listen to me. Maybe you can sort it out?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <dark_gray>Black Bunny"),
                Advent2025Npc.BLACK_BUNNY,
                "The clover is mine. I found it first!",
                "Brown was too busy napping, and Gold was off chasing butterflies."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <color:#c2b280>Brown Bunny"),
                Advent2025Npc.BROWN_BUNNY,
                "Nonsense! Black hoards everything. Gold and I should split it.",
                "Besides, I'm the eldest. I deserve extra."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <gold>Gold Bunny"),
                Advent2025Npc.GOLD_BUNNY,
                "Eldest? Black is the fastest, and Brown is the loudest.",
                "But I'm the prettiest, obviously, I should get it!",
                "Now that you spoken to all of us, what's your opinion? Who should have it?"
            )
            .addUserChoice("choice", "You")
            .addUserChoice("choice", "Brown")
            .addUserChoice("choice", "Black")
            .addUserChoice("split", "Split it!")
            .label("choice"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <gold>Gold Bunny"),
                Advent2025Npc.GOLD_BUNNY,
                "Split it into 3 patches? Hmm...",
                "<gray><italic>Twitching</gray>\nGreat idea! Now we can all nap happily!"
            )
            .label("split"),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <color:#c2b280>Brown Bunny"),
                Advent2025Npc.BROWN_BUNNY,
                "<gray><italic>Chuckling</gray>\nAgreed, we can share. Though I'll eat mine slowest."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <dark_gray>Black Bunny"),
                Advent2025Npc.BLACK_BUNNY,
                "<gray><italic>Grumbling</gray>\nFine, let's share. But I get the biggest bite."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "You're a miracle worker. The bunnies have finally agreed on something.",
                "Maybe it'll keep 'em quiet for a day."
            )
        )
    ),
    SAD_COW(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <blue>Dairy Farmer"),
                Advent2025Npc.DAIRY_FARMER,
                "Oh, hello! I've got a bit of a problem.",
                "My oldest cow, the <blue>Moo Cow</blue>, has been moping in the corner of the barn.",
                "She's usually so cheerful, but ever since the festival decorations went up, she's just... sad.",
                "I think she's feeling left out. Maybe she wants to be part of the celebrations too?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <blue>Moo Cow"),
                Advent2025Npc.MOO_COW,
                "Mooh... Everyone's so busy with the tree and the presents.",
                "I just wanted to help, but what can a cow do?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <blue>Dairy Farmer"),
                Advent2025Npc.DAIRY_FARMER,
                "Oh my, that sounds serious! Best ask the Reindeer Herder for advice. He knows best what's good for animals."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <color:#a52a2a>Reindeer Herder"),
                Advent2025Npc.REINDEER_HERDER,
                "I know how to care for animals, but curing their depression...",
                "Best include the <light_purple>Wizard</light_purple> in this. Her magic will do the trick."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Ask the <light_purple>Wizard"),
                Advent2025Npc.WIZARD,
                "A cow who wants to join the fun? Easy!",
                "Bring her a wreath of holly from the forest. Cows love festive accessories.",
                "And maybe... sing her a carol? She's got a good ear for music."
            ),
            new AdventQuestStageCollectItems(
                parseMiniMessage("Find some <green>Holly"),
                List.of(
                    Vec3i.of(93, 67, 419),
                    Vec3i.of(85, 70, 419),
                    Vec3i.of(63, 72, 433),
                    Vec3i.of(46, 70, 427),
                    Vec3i.of(29, 67, 398),
                    Vec3i.of(46, 74, 368),
                    Vec3i.of(73, 81, 327)
                ),
                (i, vec) -> new ItemStack(Material.SPRUCE_SAPLING)
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Bring it to the <blue>Moo Cow"),
                Advent2025Npc.MOO_COW,
                "Mooh! I do feel festive now!",
                "Maybe I'll moo a carol for the village tonight!"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <blue>Dairy Farmer"),
                Advent2025Npc.DAIRY_FARMER,
                "Oh, you've made her day! Here, take this warm mug of cocoa, on the house."
            )
        )
    ),
    SCARF(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <aqua>Snowman"),
                Advent2025Npc.SNOWMAN,
                "Oh no! My beautiful scarf is gone!",
                "The wind blew it away while I was waving at the villagers.",
                "Without it, I feel so cold. Could you please help me find it?",
                "The <light_purple>Baker</light_purple> was outside earlier. Maybe they saw where it went?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <light_purple>Baker"),
                Advent2025Npc.BAKER,
                "Oh dear, that scarf! I did see it flying off toward the forest.",
                "I couldn't leave the shop, but I think the <blue>Moo Cow</blue> was grazing nearby.",
                "She might have seen where it landed. Why don't you ask her?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <blue>Moo Cow"),
                Advent2025Npc.MOO_COW,
                "Oh, hello there! Yes, I saw something colorful caught in the tallest tree.",
                "Moo! It's just over yonder. I can't reach it, but you might!",
                "Good luck! I hope you find it."
            ),
            new AdventQuestStageCollectItems(
                parseMiniMessage("Pick up the <red>Scarf"),
                List.of(Vec3i.of(395, 89, 434)),
                (i, vec) -> new ItemStack(Material.RED_BANNER)
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <aqua>Snowman"),
                Advent2025Npc.SNOWMAN,
                "Oh, you found it! Thank you so much!",
                "Now I can stay warm and keep greeting everyone.",
                "Here, the Baker wanted you to have this cookie as a thank you.",
                "Merry Christmas!"
            )
        )
    ),
    CINNAMON(
        0, () -> new AdventQuestStaged(
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <light_purple>Baker"),
                Advent2025Npc.BAKER,
                "Oh dear, I almost forgot! My special Christmas cake needs just a pinch of cinnamon.",
                "It's the secret ingredient, but I'm all out. Could you ask the <blue>Moo Cow</blue> for some?",
                "She has the last bit, but she's very fond of it. Be gentle!"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <blue>Moo Cow"),
                Advent2025Npc.MOO_COW,
                "My cinnamon? Oh, I don't know...",
                "It's the last of my winter supply. Moo!",
                "But... I suppose the cake is for everyone in the village.",
                "Very well. Here you go. Just promise me you'll share a slice?"
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Talk to the <light_purple>Baker"),
                Advent2025Npc.BAKER,
                "You did it! Thank you! And of course, the first slice is for the Moo Cow.",
                "Here, take this to her. Nothing says Christmas like sharing with friends."
            ),
            new AdventQuestStageTalkToNpc(
                parseMiniMessage("Return to the <blue>Moo Cow"),
                Advent2025Npc.MOO_COW,
                "Oh! It's delicious! Maybe sharing <bold>is</bold> better than keeping things to myself.",
                "Thank you for reminding me. Moo!"
            )
        )
    ),
    POEM(
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Talk to the <aqua>Snow Fox"),
            Advent2025Npc.SNOW_FOX,
            "I've never gotten a gift before. I wonder what it feels like...",
            "Everyone else seems so happy with their presents. Could you help me find mine?"
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Ask <color:#800080>Lila"),
            Advent2025Npc.LILA,
            "Oh, the Snow Fox has never received a gift? That won't do.",
            "As a <italic>writer</italic>, I can help! I've been collecting notes from the villagers about them.",
            "If you gather them all, I'll turn them into something special."
        ),
        new AdventQuestStageCollectItems(
            parseMiniMessage("Find Lila's <gold>Notes"),
            List.of(
                Vec3i.of(239, 76, 350), // train tracks
                Vec3i.of(203, 68, 352),
                Vec3i.of(174, 75, 362), // Wizard tower
                Vec3i.of(140, 67, 340), // Cat
                Vec3i.of(86, 81, 355), // Postman's
                Vec3i.of(66, 85, 247), // Farmer
                Vec3i.of(127, 67, 212), // Ice
                Vec3i.of(107, 69, 123), // Librarian
                Vec3i.of(314, 79, 124), // Herder
                Vec3i.of(466, 71, 395) // Chicken
            ),
            (i, vec) -> new ItemStack(Material.BOOK)
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Bring them to <color:#800080>Lila"),
            Advent2025Npc.LILA,
            "Here you go! I've turned them into a poem just for you.",
            "<gray><font:uniform><italic>Snow Fox, so bright, your joy is our light.",
            "<gray><font:uniform><italic>With every leap and playful spree, you bring us all glee.",
            "<gray><font:uniform><italic>Your kindness is a gift, pure and true: Thank you for simply being you!",
            "Now bring that to the <aqua>Snow Fox</aqua> and I'm sure it will make her day."
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Bring it to the <aqua>Snow Fox"),
            Advent2025Npc.SNOW_FOX,
            "A poem? For me? Oh, this is the best gift ever!",
            "Thank you, thank you! I'll keep it forever."
        )
    ),
    FABLE(
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Talk to <gray>Mistletoe"),
            Advent2025Npc.CAT,
            "Purr... Ah, the holidays can be so hectic, meow. Let me tell you a story to remind us all what matters.",
            "<gray><font:uniform>Once, a Little Star fell to earth...",
            "Meows and mittens! I've forgotten how it goes! Meow, could you help me?",
            "I think the next part involved a <aqua>fox</aqua>..."
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Ask the <aqua>Snow Fox"),
            Advent2025Npc.SNOW_FOX,
            "Oh, I remember!",
            "<gray><font:uniform>The <underlined>fox</underlined>, the <underlined>cow</underlined>, and the <underlined>cat</underlined> all helped the Little Star.",
            "<gray><font:uniform>Together, they lifted it back into the sky. It was quite the adventure!",
            "And then the stars said... uhm, uh...",
            "Tails and twigs, I forgot! Perhaps a <blue>cow</blue> would remember..."
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Ask the <blue>Moo Cow"),
            Advent2025Npc.MOO_COW,
            "Moo! <gray><font:uniform>The stars said, The brightest light isn’t the one that shines alone, but the one that brings others together.",
            "And you never heard that story?! Huff! Everybody in the stable learns this one from the crib on.",
            "It’s as true as the sunrise over the pasture. Moo!"
        ),
        new AdventQuestStageTalkToNpc(
            parseMiniMessage("Return to <gray>Mistletoe"),
            Advent2025Npc.CAT,
            "Ah yes, meow! Thank you. Now, let’s share this tale with everyone.",
            "Purr, may it remind us all that kindness and unity are the true magic of Christmas."
        )
    ),
    ;

    private final int adventWorldIndex;
    private final Supplier<AdventQuest> questSupplier;
    @Setter private AdventQuest instance;

    Advent2025Quest(final AdventQuestStage... stages) {
        this.adventWorldIndex = 0;
        this.questSupplier = () -> new AdventQuestStaged(stages);
    }

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
