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
              List.of(Vec3i.of(210, 76, 65)),
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
