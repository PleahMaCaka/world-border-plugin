package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.Example
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

@Suppress("unused")
object ShopGui : Listener {

    private const val SIZEOF = 6 * 9

    private val ITEMS_ON = arrayOf(
        10, 12, 14, 16, 19, 21, 23, 25, 28, 30, 32, 34, 37, 39, 41, 43
    )

    private val items: Array<ShopItem> = arrayOf(
        /**
         * Page 1
         */
        ShopItem(1, Material.OAK_LOG, "원목", 128, 1),
        ShopItem(1, Material.COAL, null, 64, 1),
        ShopItem(1, Material.IRON_INGOT, null, 32, 1),
        ShopItem(1, Material.GOLD_INGOT, null, 32, 1),

        ShopItem(1, Material.DIAMOND, null, 16, 1),
        ShopItem(1, Material.EMERALD, null, 16, 1),
        ShopItem(1, Material.REDSTONE, null, 128, 1),
        ShopItem(1, Material.LAPIS_LAZULI, null, 128, 1),

        ShopItem(1, Material.NETHERITE_INGOT, null, 2, 1),
        ShopItem(1, Material.QUARTZ, null, 128, 1),
        ShopItem(1, Material.GLOWSTONE, null, 128, 1),
        ShopItem(1, Material.PRISMARINE_SHARD, null, 32, 1),

        ShopItem(1, Material.COBBLESTONE, null, 777, 1),
        ShopItem(1, Material.CLAY, null, 96, 1),
        ShopItem(1, Material.APPLE, null, 64, 1),
        ShopItem(1, Material.ROTTEN_FLESH, null, 128, 1),

        /**
         * Page 2
         */
        // ...
    )

    @EventHandler
    fun clickHandle(event: InventoryClickEvent) {
        if (Example.IS_DEV) Example.instance.logger.info("Prevent click")
        if (event.inventory == inv) event.isCancelled = true
    }

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("상점"))

    init {
        for (i in items.indices) {
            val item = items[i]
            val slot = ITEMS_ON[i]
            val itemStack = ItemStack(item.material).apply {
                itemMeta = itemMeta.apply {
                    lore(
                        mutableListOf(
                            Component.text("필요한 수: ${item.needs}")
                                .color(TextColor.color(0x00FF00))
                                .decoration(TextDecoration.ITALIC, false),
                            Component.text("확장권 수: ${item.amount}")
                                .color(TextColor.color(0x00FFFF))
                                .decoration(TextDecoration.ITALIC, false),
                        )
                    )
                }
            }
            inv.setItem(slot, itemStack)
        }

        for (i in 0 until SIZEOF) {
            val emptyItem = ItemStack(Material.GRAY_STAINED_GLASS_PANE).apply {
                itemMeta = itemMeta.apply { displayName(Component.text(" ")) }
            }
            if (inv.getItem(i) == null) inv.setItem(i, emptyItem)
        }
    }

}