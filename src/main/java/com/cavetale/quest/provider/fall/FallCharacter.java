package com.cavetale.quest.provider.fall;

import com.cavetale.quest.QuestPlugin;
import com.cavetale.quest.config.EntityConfig;
import com.cavetale.quest.config.SpawnLocationConfig;
import com.cavetale.quest.entity.EntityInstance;
import com.cavetale.quest.entity.EntityTrigger;
import com.cavetale.quest.entity.behavior.EntityLookAtPlayerBehavior;
import com.cavetale.quest.entity.data.EntityProfileData;
import com.cavetale.quest.script.viewer.Viewership;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public enum FallCharacter {
    STATION_MASTER(
        text("Station Master", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJhNWU5NDJjNjFiNjI0Nzk3MmMwOGY2ZDU1MjYxNGIxOGVlZWE3OWM4MjMwNTBiNWQ4YTNmMGUxMWFiMDNlMyJ9fX0=",
        153.47, 68.00, 266.46, -98.82f
    ),
    TRAIN_CONDUCTOR(
        text("Train Conductor", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE3NjBhZWRiNTFhZmRhNWM1OGVmYzAyYzUzZGNkNzVmMzRiMmE1YjY4M2FlN2E2ZjRhYWM4NzliNDFiNmM0MSJ9fX0=",
        165.47, 68.00, 259.45, -50.32f
    ),
    FARMHAND_BARN(
        text("Farmhand", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwMWVkZTFkYTNlOWQzMGI5ZWQ0MWM3YjFjYWZhM2Q5MTBhMjQwODZhYTllYmJhM2M2YjBhMDFhZmFhNDliZiJ9fX0=",
        328.46, 67.00, 178.44, 0.72f
    ),
    FARMHAND_WHEAT(
        text("Farmhand", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwMWVkZTFkYTNlOWQzMGI5ZWQ0MWM3YjFjYWZhM2Q5MTBhMjQwODZhYTllYmJhM2M2YjBhMDFhZmFhNDliZiJ9fX0=",
        269.44, 67.00, 242.58, -9.73f
    ),
    FARMHAND_PUMPKINS(
        text("Farmhand", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwMWVkZTFkYTNlOWQzMGI5ZWQ0MWM3YjFjYWZhM2Q5MTBhMjQwODZhYTllYmJhM2M2YjBhMDFhZmFhNDliZiJ9fX0=",
        318.60, 67.00, 249.45, 136.56f
    ),
    PRIEST(
        text("Priest", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI1NTU3YmQ1YTgyYjBjMjEwNDVlMzIyMGMzMzgwYWM5ZTBlZDQ2ZGM4M2I1ZjAwYTlkZjJlM2QzZTUwNDVhMiJ9fX0=",
        224.54, 67.00, 278.58, 162.46f
    ),
    GOODS_MASTER(
        text("Goods Master", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzI3YzNlMTY5Yjk5MWFkYjk1N2YxOWRjMzYyN2E2MGNmZmY4NzdmZDg5NDRhNjFiZDY3MDhlYmQyMGM2M2ZkYSJ9fX0=",
        178.71, 67.00, 256.53, 4.63f
    ),
    FISHERMAN(
        text("Fisherman", GOLD),
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM1MWQ3OGNlNDg5MzliYTg5YjllOTFlODk2MjQ2Mjc4NjEwOGUxNTczNzViOWY0MDg2ZjVjNjdkZGE2YzAyOSJ9fX0=",
        271.49, 66.00, 327.49, -158.52f
    ),
    ;

    private final EntityType entityType;
    private final Component displayName;
    private final String texture;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private EntityInstance instance;

    FallCharacter(
        final Component displayName,
        final String texture,
        final double x,
        final double y,
        final double z,
        final float yaw
    ) {
        this.entityType = EntityType.MANNEQUIN;
        this.displayName = displayName;
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
    }

    public void enable() {
        if (entityType == EntityType.MANNEQUIN) {
            EntityConfig config = new EntityConfig();
            SpawnLocationConfig spawn = new SpawnLocationConfig();
            config.setEntityType(EntityType.MANNEQUIN);
            config.setDisplayName(displayName);
            config.addEntityData(new EntityProfileData(texture, null));
            config.addEntityTrigger(
                new EntityTrigger() {
                    @Override public void onPlayerClick(EntityInstance entityInstance, Player player) {
                        FallProvider.getInstance().onPlayerClick(player, FallCharacter.this);
                    }
                }
            );
            config.addEntityBehavior(new EntityLookAtPlayerBehavior(1));
            spawn = new SpawnLocationConfig(
                FallProvider.SERVER,
                FallProvider.WORLD,
                x, y, z, yaw, 0f
            );
            instance = new EntityInstance(
                config,
                spawn,
                Viewership.global()
            );
            QuestPlugin.questPlugin().getEntities().enableEntityInstance(instance);
        }
    }
}
