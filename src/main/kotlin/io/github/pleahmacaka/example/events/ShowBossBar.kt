package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.GameStatus
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ShowBossBar : Listener {

    @EventHandler
    fun showBossBar(event: PlayerJoinEvent) {
        GameStatus.bar.addViewer(event.player)
    }

    @EventHandler
    fun hideBossBar(event: PlayerQuitEvent) {
        GameStatus.bar.removeViewer(event.player)
    }

}