package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.items.ExpandBorderItem
import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

@Suppress("unused")
class ShopItem(
    page: Int,
    name: Named? = null,
    material: Material,

    var needs: Int,    // Count of require item
    var amount: Int,   // Amount of expander

    lore: Array<Component>? = null,
    loc: Int? = null,


    /**
     * Allowed tags for exchange, null for only material
     */
    val allowList: MutableList<Material>? = null,
) : ShopItemBase(
    page = page, name = name, material = material, lore = lore, loc = loc
) {
    init {
        if (Example.IS_DEV) Example.instance.logger.info("ShopItem Registered: $name, $material")
        Example.instance.server.pluginManager.registerEvents(this, Example.instance)
    }

    @EventHandler
    private fun exchange(event: InventoryClickEvent) {
        if (event.clickedInventory != ShopGui.inv) return
        if (!event.isLeftClick) return
        if (event.currentItem?.type != material) return

        event.isCancelled = true

        val items: List<ItemStack?> = event.whoClicked.inventory.contents.filter { it?.type == event.currentItem?.type }
        val stackSum = items.sumOf { it?.amount ?: 0 }

        Example.instance.logger.info("StackSum: $stackSum, Needs: $needs")

        if (stackSum < needs)
            return event.whoClicked.sendMessage("§c[$stackSum/$needs] 아이템이 충분하지 않습니다!")

        var removed = 0
        for (item in items) {
            if (removed >= needs) break
            if (item == null) continue
            val left = needs - removed
            if (item.amount > left) {
                item.amount -= left
                removed += left
            } else {
                removed += item.amount
                item.amount = 0
            }
        }

        // if inventory is full, drop the item
        if (removed < needs)
            event.whoClicked.world.dropItem(event.whoClicked.location, ExpandBorderItem.itemStack)

        val player = event.whoClicked
        player.inventory.addItem(ExpandBorderItem.itemStack)
        player.sendMessage("§a경계 확장기를 구매하였습니다!")
    }
}