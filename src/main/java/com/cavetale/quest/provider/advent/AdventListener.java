package com.cavetale.quest.provider.advent;

import com.cavetale.core.event.hud.PlayerHudEvent;
import com.cavetale.core.event.hud.PlayerHudPriority;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;

@RequiredArgsConstructor
public final class AdventListener implements Listener {
    private final AdventProvider advent;
    private BossBar bossBar;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, advent.getPlugin());
        bossBar = BossBar.bossBar(
            text(""),
            1f,
            BossBar.Color.GREEN,
            BossBar.Overlay.NOTCHED_10
        );
    }

    @EventHandler
    private void onPlayerHud(PlayerHudEvent event) {
        final Player player = event.getPlayer();
        if (!AdventProvider.ADVENT_SERVER.isThisServer() || !AdventProvider.ADVENT_WORLDS.contains(player.getWorld().getName())) {
            return;
        }
        if (!Session.isEnabled(player)) return;
        bossBar.name(empty());
        for (PlayerQuest playerQuest : Session.of(player).getActiveQuests()) {
            if (playerQuest.getQuest() instanceof AdventQuest adventQuest) {
                adventQuest.makeBossBar(playerQuest, bossBar);
                event.bossbar(PlayerHudPriority.HIGH, bossBar);
                break;
            }
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return;
        final Player player = event.getPlayer();
        if (!AdventProvider.ADVENT_SERVER.isThisServer() || !AdventProvider.ADVENT_WORLDS.contains(player.getWorld().getName())) {
            return;
        }
        for (PlayerQuest playerQuest : Session.of(player).getActiveQuests()) {
            if (playerQuest.getQuest() instanceof AdventQuest adventQuest) {
                if (!player.getWorld().getName().equals(adventQuest.getAdventWorldName())) continue;
                adventQuest.onPlayerMoveBlock(playerQuest, Vec3i.of(event.getTo().getBlock()));
            }
        }
    }
}
