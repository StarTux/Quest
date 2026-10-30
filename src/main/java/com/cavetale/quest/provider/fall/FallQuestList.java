package com.cavetale.quest.provider.fall;

import com.cavetale.quest.QuestPlugin;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import static com.cavetale.core.struct.Vec3i.vec3i;

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
                new FallQuestStageAuto(
                    player -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kite member halloween_2026_part1 " + player.getName());
                    }
                )
            );
        }
    }

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
