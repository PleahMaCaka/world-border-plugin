package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.GameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

object CheckJoinedWorld : Listener {

    private val server = Example.instance.server

    @Suppress("DuplicatedCode")
    @EventHandler
    fun whenJoinWorlds(event: PlayerTeleportEvent) {
        val player = event.player
        val dest = event.to

        when (event.cause) {
            PlayerTeleportEvent.TeleportCause.NETHER_PORTAL -> {
                // world로부터 왔다는 의미, 원래 이런식으로 쓰는거 아님...
                if (player.world.name != "world") {
                    val over = server.getWorld("world")
                    return GameManager.setBossbar("현재 월드보더 크기 : ${over?.worldBorder?.size?.toInt()}")
                }

                val nether = server.getWorld("world_nether")
                GameManager.setBossbar("현재 월드보더 크기 : ${nether?.worldBorder?.size?.toInt()}")

                if (GameManager.netherJoined) return
                GameManager.netherJoined = true

                val border = nether?.worldBorder
                border?.center = dest
                border?.size = GameManager.initBorderSize.toDouble()
            }

            PlayerTeleportEvent.TeleportCause.END_PORTAL -> {
                val end = server.getWorld("world_the_end")
                GameManager.setBossbar("현재 월드보더 크기 : ${end?.worldBorder?.size?.toInt()}")

                if (GameManager.endJoined) return
                GameManager.endJoined = true

                val border = end?.worldBorder
                border?.center = dest
                border?.size = GameManager.initBorderSize.toDouble()
            }

            else -> return
        }
    }

}