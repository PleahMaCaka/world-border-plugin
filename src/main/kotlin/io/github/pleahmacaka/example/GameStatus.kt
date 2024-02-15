package io.github.pleahmacaka.example

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.bossbar.BossBar.Overlay
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

object GameStatus {

    val bar = BossBar.bossBar(
        Component.text("대기중"), 1.0f, BossBar.Color.BLUE, Overlay.PROGRESS
    )

    fun showAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.addViewer(player as Audience)
        }
    }

    fun removeAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.removeViewer(player as Audience)
        }
    }

}