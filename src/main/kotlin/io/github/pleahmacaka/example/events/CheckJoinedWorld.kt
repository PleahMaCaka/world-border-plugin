package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.GameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object CheckJoinedWorld : Listener {

    @EventHandler
    fun whenJoinWorlds(event: PlayerTeleportEvent) {
        val player = event.player
        val dest = event.to
        player.sendMessage("Cause: ${event.cause}")
        when (event.cause) {
            PlayerTeleportEvent.TeleportCause.NETHER_PORTAL -> {
                if (GameManager.netherJoined) return
                GameManager.netherJoined = true

                val border = Example.instance.server.getWorld("world_nether")?.worldBorder
                border?.center = dest
                border?.size = GameManager.netherBorderSize.toDouble()
            }

            PlayerTeleportEvent.TeleportCause.END_PORTAL -> {
                if (GameManager.endJoined) return
                GameManager.endJoined = true

                val border = Example.instance.server.getWorld("world_the_end")?.worldBorder
                border?.center = dest
                border?.size = GameManager.endBorderSize.toDouble()
            }

            else -> return
        }
    }

}