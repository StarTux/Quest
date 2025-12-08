package com.cavetale.quest.entity.data;

import com.destroystokyo.paper.profile.ProfileProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mannequin;
import static io.papermc.paper.datacomponent.item.ResolvableProfile.resolvableProfile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class EntityProfileData implements EntityData {
    private String texture;
    private String signature;

    @Override
    public boolean apply(Entity entity) {
        if (entity instanceof Mannequin mannequin) {
            mannequin.setProfile(
                resolvableProfile()
                .addProperty(new ProfileProperty("textures", texture, signature))
                .build()
            );
            return true;
        } else {
            return false;
        }
    }
}
