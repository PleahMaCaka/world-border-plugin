package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.GameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

object RespawnToShowBossBar : Listener {

    @EventHandler
    fun showBossBar(event: PlayerRespawnEvent) {
        if (event.respawnReason == PlayerRespawnEvent.RespawnReason.DEATH)
            GameManager.setBossbar(
                "현재 월드보더 크기 : ${
                    Example.instance.server.getWorld("world")
                        ?.worldBorder?.size?.toInt()
                }"
            )
    }

}