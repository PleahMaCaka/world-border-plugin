package io.github.pleahmacaka.example

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.bossbar.BossBar.Overlay
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

object GameStatus {

    var started = false
    var borderSize = 8

    var bar = BossBar.bossBar(
        Component.text("대기중"), 1.0f, BossBar.Color.BLUE, Overlay.PROGRESS
    )

    fun setName(name: String) {
        bar.name(Component.text(name))
    }

    fun showAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.addViewer(player as Audience)
        }
    }

    fun hideAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.removeViewer(player as Audience)
        }
    }

}