package io.github.pleahmacaka.example.items

import io.github.pleahmacaka.example.GameManager
import io.github.pleahmacaka.example.gui.RewardGui
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


    private val over: Int
        get() = 2 * GameManager.expanderMultiplier

    private val nether: Int
        get() = 4 * GameManager.expanderMultiplier

    private val end: Int
        get() = 6 * GameManager.expanderMultiplier

    val itemStack = ItemStack(Material.GOAT_HORN).apply {
        itemMeta = itemMeta.apply {
            displayName(Component.text("월드 경계 확장기"))
            lore(
                mutableListOf(
                    Component.text("월드 경계를 확장합니다.").decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0x00FF00)),
                    Component.text(" "),
                    Component.text("오버월드: ${over}칸").decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0xFF8100)),
                    Component.text("네더월드: ${nether}칸").decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0xAC2020)),
                    Component.text("엔더월드: ${end}칸").decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0xA9A3A3)),
                )
            )
        }
    }

    /**
     * Not considered stack items
     */
    @EventHandler
    fun onUse(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item

        if (item != itemStack) return

        // Expand the world border
        val world = player.world
        val worldType = world.environment
        val worldBorder = world.worldBorder
        val size = worldBorder.size
        worldBorder.size = when (worldType) {
            World.Environment.NORMAL -> size + over
            World.Environment.NETHER -> size + nether
            World.Environment.THE_END -> size + end
            else -> {
                player.sendMessage("이 월드는 확장할 수 없습니다. 관리자에게 문의하세요.")
                size
            }
        }
        GameManager.borderSize = worldBorder.size.toInt()

        player.sendMessage(
            Component.text("월드 경계를 확장했습니다.").color(TextColor.color(0x00FF00))
        )

        // Remove expander
        GameManager.expandingCount += 1
        item.amount -= 1

        // Give the one random reward
        val random = (0..RewardGui.getRewardSize()).random() - 1

        if (random < 0) return player.sendMessage("청크 확장권의 보상이 설정되어 있지 않습니다. 관리자에게 문의하세요.")

        // if inventory is full, drop the item
        // generally expander will replace that index,
        // but before item is can be gotten in inventory until the item is removed
        if (player.inventory.firstEmpty() == -1) {
            player.world.dropItem(player.location, RewardGui.inv.contents[random]!!)
            return player.sendMessage("§c인벤토리가 가득 찼습니다. 보상이 바닥에 떨어졌습니다.")
        }

        player.sendMessage(random.toString())
        player.inventory.addItem(RewardGui.inv.contents[random]!!)
    }

    @EventHandler
    fun preventPlace(event: PlayerInteractEvent) {
        val item = event.item
        if (item == itemStack) event.isCancelled = true
    }

}