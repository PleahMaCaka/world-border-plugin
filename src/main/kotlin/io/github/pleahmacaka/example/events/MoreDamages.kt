package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.GameManager
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

object MoreDamages : Listener {

    /**
     * 데미지를 받았을때 플레이어가 아니라면 리턴하고
     * 좌표 GameManager.startedAt 좌표 (시작점)을 기준으로 100블럭당 맞는 데미지가 10%씩 증가
     * GameManager.startedAt은 Int[]로 되어있어서 Location으로 변환해야 함
     * 지옥, 엔더월드는 데미지가 1.5배 +a
     * for GPT
     */
    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        val player = event.entity as Player
        val location =
            GameManager.startedAt.let { Location(player.world, it[0].toDouble(), it[1].toDouble(), it[2].toDouble()) }
        val distance = location.distance(player.location)
        val damage = event.damage

        if (distance > 50)
            event.damage = damage + (distance / 100).toInt() * ((1 + (GameManager.damageMultiplier / 100)))
        if (player.world.name == "world_nether" || player.world.name == "world_the_end")
            event.damage = (damage * 1.5) + (GameManager.damageMultiplier / 100)
    }
}