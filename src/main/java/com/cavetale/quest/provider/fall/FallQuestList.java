package com.cavetale.quest.provider.fall;

import com.cavetale.quest.QuestPlugin;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import static com.cavetale.core.struct.Vec3i.vec3i;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public enum FallQuestList {

    FIRST {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.STATION_MASTER, "Talk to the Station Master",
                    List.of(
                        " Mornin'! See that Goods Master over yonder?",
                        "He's flappin' around like an octopus with too many arms and no sense of where to put 'em.",
                        "Why don't you go give him a hand? Or eight."
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.GOODS_MASTER, "Talk to the Goods Master",
                    List.of(
                        "Oh, bless ya for comin'!",
                        "I've got pies comin' out my ears and not enough hands to carry 'em.",
                        "There's ten cooling on the ground by the loading dock.",
                        "Could you take 'em over to the church?"
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageCollectItems(
                    "Ten pies, church. And no samplin'. I counted.",
                    Material.CAKE,
                    List.of(
                        vec3i(151, 68, 291),
                        vec3i(130, 70, 275),
                        vec3i(137, 68, 257),
                        vec3i(140, 68, 255),
                        vec3i(131, 67, 248),
                        vec3i(137, 67, 232),
                        vec3i(155, 68, 246),
                        vec3i(163, 74, 249),
                        vec3i(161, 68, 230),
                        vec3i(163, 71, 201)
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.PRIEST, "Bring them to the Priest",
                    List.of(
                        "Pies! And still warm! The good Pumpkin provides, and so do you.",
                        "Thank you so much.",
                        "Now, I hear the fellas down by the lake could use some help.",
                        "Why don't you pay them a visit?"
                    )
                )
            );
        }
    },

    SECOND {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FISHERMAN, "Talk to the Fisherman",
                    List.of(
                        "Howdy!",
                        "The Farmer lost his weddin' band out here while fishin' last week, and he's been mopin' ever since.",
                        "It's gotta be down there somewhere. Cast a line and see if you can reel it in."
                    )
                )
            );
            getInstance().addStage(new FallQuestStageFishing());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FISHERMAN, "Talk to the Fisherman",
                    List.of(
                        "Well I'll be!",
                        "Take that up to the Farm Hand in the wheat fields.",
                        "He'll make sure it gets home.",
                        "And tell him I said hello!"
                    )
                )
            );
        }
    },

    THIRD {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_WHEAT, "Bring it to the Farmhand",
                    List.of(
                        "Is that the Farmer's ring?",
                        "He'll be tickled! I'll see he gets it.",
                        "But first, harvest has to be done before the frost rolls in, and I'm a few bushels short.",
                        "Think you could help me out?"
                    )
                )
            );
            getInstance().addStage(new FallQuestStageHarvest());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_WHEAT, "Return it to the Farmhand",
                    List.of(
                        "That's the stuff! The harvest is saved.",
                        "Now, the pumpkin patch is parched. Meet me over there?"
                    )
                )
            );
        }
    },

    FOURTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_PUMPKINS, "Meet the Farmhand",
                    List.of(
                        "Look at these poor things, dry as a bone.",
                        "Take your watering can and give these patches a good soakin'."
                    )
                )
            );
            getInstance().addStage(new FallQuestStageWatering());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_PUMPKINS, "Return to the Farmhand",
                    List.of(
                        "Look at 'em perk up! Thanks again.",
                        "Come meet me at the barn."
                    )
                )
            );
        }
    },

    FIFTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_BARN, "Meet the Farmhand",
                    List.of(
                        "The cow got loose again, and the milkin' won't do itself.",
                        "Lure 'er back into the pen, where she's safe and sound."
                    )
                )
            );
            getInstance().addStage(new FallQuestStageHerdCow());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_BARN, "Return to the Farmhand",
                    List.of(
                        "Fresh milk and no wanderers. It's all in a day's work!",
                        "You've been a great help to this farm.",
                        "Thank you for everything.",
                        "The ring, the harvest, the cows, all of it.",
                        "Head back to the Station Master.",
                        "He's got something for ya."
                    )
                )
            );
        }
    },

    SIXTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.STATION_MASTER, "Return to the Station Master",
                    List.of(
                        "Heard you've been busier than a squirrel in October.",
                        "Here's your reward. Happy Fall!"
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageAuto(player -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kite member halloween_2026_part1 " + player.getName()))
            );
        }
    },

    // Spooky

    SEVENTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.STATION_MASTER, "Talk to the Station Master",
                    List.of(
                        "That train hasn't moved since sundown and the passengers are gettin' antsy.",
                        "Could you check on her? The Conductor can tell you what she needs."
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.TRAIN_CONDUCTOR, "Talk to the Train Conductor",
                    List.of(
                        "This ol' gal's been makin' odd noises all night.",
                        "Care to feed her for me? I promise she won't bite.",
                        "Come see me when you're done."
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageCollectItems(
                    "Grab some Coal",
                    Material.COAL,
                    List.of(
                        vec3i(170, 70, 243),
                        vec3i(169, 68, 242),
                        vec3i(172, 68, 242),
                        vec3i(171, 67, 245)
                    )
                )
            );
            getInstance().addStage(new FallQuestStageFeedTrain());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.TRAIN_CONDUCTOR, "Return to the Train Conductor",
                    List.of(
                        "Thank ya kindly!",
                        "She's purrin' now, but she still won't budge.",
                        "And I saw somethin' funky down at the lake that's givin' me the heebie-jeebies.",
                        "Could you go see what's goin' on?"
                    )
                )
            );
        }
    },

    EIGHTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FISHERMAN, "Talk to the Fisherman",
                    List.of(
                        "Somethin's out there.",
                        "A long neck risin' outta the water at night, humps and all.",
                        "The locals call it Nessi.",
                        "Oh, and a fella on a black horse rode by earlier.",
                        "Real polite. Didn't see his head, though."
                    )
                )
            );
            getInstance().addStage(new FallQuestStageHerdNessie());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FISHERMAN, "Return to the Fisherman",
                    List.of(
                        "She's just a baby! Look at her!",
                        "Well, don't I feel silly.",
                        "Go find the Farm Hand in the wheat field. He's been jumpy all day."
                    )
                )
            );
        }
    },

    NINTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_WHEAT, "Talk to the Farmhand",
                    List.of(
                        "Thank goodness, a person!",
                        "I lost my Lucky Beetroot in the windmill and the wheat's gone all twisty.",
                        "Spooky things in there! Make it through and grab it for me?"
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageCollectItems(
                    "Find the Lucky Beetroot",
                    Material.BEETROOT,
                    List.of(
                        vec3i(265, 67, 180)
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_WHEAT, "Talk to the Farmhand",
                    List.of(
                        "My lucky beet! Bless you!",
                        "Now head to the pumpkin patch.",
                        "Big Jack's been grumblin'."
                    )
                )
            );
        }
    },

    TENTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_PUMPKINS, "Head to the Pumpkin Patch",
                    List.of(
                        "Big Jack's been hungry since the moon came up, and he gets cranky when he's hungry.",
                        "Toss some baby pumpkins in his mouth, would ya?"
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageCollectItems(
                    "Find the Baby Pumpkins",
                    Material.PUMPKIN,
                    List.of(
                        vec3i(324, 68, 254),
                        vec3i(314, 66, 271),
                        vec3i(320, 66, 294),
                        vec3i(336, 66, 298),
                        vec3i(355, 67, 282),
                        vec3i(370, 67, 288),
                        vec3i(379, 67, 270),
                        vec3i(367, 67, 247)
                    )
                )
            );
            getInstance().addStage(new FallQuestStageFeedJack());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_PUMPKINS, "Return to the Farmhand",
                    List.of(
                        "Ahh, much better! Thank you again.",
                        "Meet me at the barn."
                    )
                )
            );
        }
    },

    ELEVENTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_BARN, "Talk to the Farmhand",
                    List.of(
                        "These zombie horses are overheatin', and nobody wants a melted horse.",
                        "Give 'em a trim with some shears so they cool off and turn into skeleton horses!"
                    )
                )
            );
            getInstance().addStage(new FallQuestStageShearHorses());
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMHAND_BARN, "Return to the Farmhand",
                    List.of(
                        "Much cooler! Thank ya.",
                        "Now go see my farmer friend at the house. He's in a pickle."
                    )
                )
            );
        }
    },

    TWELFTH {
        @Override public void buildContent() {
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMER, "Talk to the Farmer",
                    List.of(
                        "Ah, there ya are. I'm the Farmer, and the Headless Horseman too.",
                        "Don't look at me like that, I do both.",
                        "I've lost my head somewhere in this house. Could you help me find it?"
                    )
                )
            );
            getInstance().addStage(new FallQuestStageCollectItems("Find the Horseman's Head", Material.CARVED_PUMPKIN, List.of(vec3i(357, 69, 209))));
            getInstance().addStage(new FallQuestStageAuto(player -> player.sendMessage(text("This one's a fake!", GOLD))));
            getInstance().addStage(new FallQuestStageCollectItems("Find the real Horseman's Head", Material.CARVED_PUMPKIN, List.of(vec3i(354, 68, 183))));
            getInstance().addStage(new FallQuestStageAuto(player -> player.sendMessage(text("This one's also a fake!", GOLD))));
            getInstance().addStage(new FallQuestStageCollectItems("Find the actual Horseman's Head", Material.CARVED_PUMPKIN, List.of(vec3i(362, 68, 199))));
            getInstance().addStage(new FallQuestStageAuto(player -> player.sendMessage(text("This one's also a fake!", GOLD))));
            getInstance().addStage(new FallQuestStageCollectItems("Find the REAL Horseman's Head", Material.JACK_O_LANTERN, List.of(vec3i(353, 68, 179))));
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.FARMER, "Return to the Farmer",
                    List.of(
                        "My head! Thank you!",
                        "Now I can finally get back to my job as the train's engineer.",
                        "She's runnin' again, so go see the Station Master."
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageTalk(
                    FallCharacter.STATION_MASTER, "See the Station Master",
                    List.of(
                        "Whistle's blowin' and the passengers are cheerin'.",
                        "Here's your reward. Happy Halloween"
                    )
                )
            );
            getInstance().addStage(
                new FallQuestStageAuto(player -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kite member halloween_2026_part2 " + player.getName()))
            );
        }
    },

    ;

    private FallQuest instance;

    public void initialize() {
        instance = new FallQuest(this, "fall2026:" + this.name().toLowerCase());
    }

    public abstract void buildContent();

    public void enable() {
        QuestPlugin.questPlugin().getQuests().enableQuest(instance);
    }
}
