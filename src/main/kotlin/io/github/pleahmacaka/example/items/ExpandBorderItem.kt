package io.github.pleahmacaka.example.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object ExpandBorderItem : Listener {

    val itemStack = ItemStack(Material.BARRIER).apply {
        itemMeta.apply {
            displayName(Component.text("월드 경계 확장기"))
            lore(
                mutableListOf(
                    Component.text("월드 경계를 확장합니다.")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0x00FF00)),
                    Component.text("오버월드: 4칸")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0xFF8100)),
                    Component.text("네더월드: 8칸")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0xAC2020)),
                    Component.text("엔더월드: 16칸")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0x17191A)),
                )
            )
        }
    }

    @EventHandler
    fun onUse(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item
        if (item == itemStack) {
            val world = player.world
            val worldType = world.environment
            val worldBorder = world.worldBorder
            val size = worldBorder.size
            val newSize = when (worldType) {
                World.Environment.NORMAL -> size + 4
                World.Environment.NETHER -> size + 8
                World.Environment.THE_END -> size + 16
                else -> size
            }
            worldBorder.size = newSize
            player.sendMessage(
                Component.text("월드 경계를 확장했습니다.")
                    .color(TextColor.color(0x00FF00))
            )
            item.amount -= 1
        }
    }

    @EventHandler
    fun preventPlace(event: PlayerInteractEvent) {
        val item = event.item
        if (item == itemStack)
            event.isCancelled = true
    }

}