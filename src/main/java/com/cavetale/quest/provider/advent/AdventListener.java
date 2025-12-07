package com.cavetale.quest.provider.advent;

import com.cavetale.core.event.hud.PlayerHudEvent;
import com.cavetale.core.event.hud.PlayerHudPriority;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import com.cavetale.quest.session.PlayerQuest;
import com.cavetale.quest.session.Session;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

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
                final Vec3i goal = adventQuest.getNextGoal(playerQuest);
                if (goal != null) {
                    bossBar.name(textOfChildren(getGoalArrow(player, goal), space(), bossBar.name()));
                }
                bossBar.name(textOfChildren(text("Day " + adventQuest.getDay(), GREEN, BOLD), space(), bossBar.name()));
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

    private Component getGoalArrow(Player player, Vec3i goal) {
        final Location playerLocation = player.getLocation();
        Vector playerDirection = playerLocation.getDirection();
        Vec3i direction = goal.subtract(Vec3i.of(playerLocation));
        double playerAngle = Math.atan2(playerDirection.getZ(), playerDirection.getX());
        double targetAngle = Math.atan2((double) direction.z, (double) direction.x);
        boolean backwards = false;
        if (!Double.isFinite(playerAngle) || !Double.isFinite(targetAngle)) {
            return Mytems.NO.asComponent();
        }
        double angle = targetAngle - playerAngle;
        if (angle > Math.PI) angle -= 2.0 * Math.PI;
        if (angle < -Math.PI) angle += 2.0 * Math.PI;
        if (Math.abs(angle) > Math.PI * 0.5) {
            return Mytems.ARROW_DOWN.asComponent();
        } else if (angle < Math.PI * -0.25) {
            return Mytems.ARROW_LEFT.asComponent();
        } else if (angle > Math.PI * 0.25) {
            return Mytems.ARROW_RIGHT.asComponent();
        } else if (angle < Math.PI * -0.125) {
            return Mytems.TURN_LEFT.asComponent();
        } else if (angle > Math.PI * 0.125) {
            return Mytems.TURN_RIGHT.asComponent();
        } else {
            return Mytems.ARROW_UP.asComponent();
        }
    }
}
