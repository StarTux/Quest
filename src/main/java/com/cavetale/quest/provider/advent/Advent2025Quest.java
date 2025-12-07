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
    FLOUR(0,
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
              List.of("Please help! The flour I was supposed to deliver to the Farmer was caught by the wind. It's been blown all over town. Can you pick it up and bring it to me?"),
              List.of("Thank you so much! I was seriously worried we couldn't finish the deliciouis Christmas cookies this year.")
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
        return "advent:quest_2025_" + String.format("%02d", ordinal() + 1);
    }

    public AdventQuest getOrCreateQuestInstance() {
        if (instance == null) {
            instance = questSupplier.get();
            instance.setQuestEnum(this);
        }
        return instance;
    }
}
