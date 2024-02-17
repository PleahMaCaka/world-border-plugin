package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.gui.ReinforceGui
import io.github.pleahmacaka.example.utils.Named
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
        ShopItem(1, Named.Text("원목"), Material.OAK_LOG, 128, 1),
        ShopItem(1, null, Material.COAL, 64, 1),
        ShopItem(1, null, Material.IRON_INGOT, 32, 1),
        ShopItem(1, null, Material.GOLD_INGOT, 32, 1),

        ShopItem(1, null, Material.DIAMOND, 16, 1),
        ShopItem(1, null, Material.EMERALD, 16, 1),
        ShopItem(1, null, Material.REDSTONE, 128, 1),
        ShopItem(1, null, Material.LAPIS_LAZULI, 128, 1),

        ShopItem(1, null, Material.NETHERITE_INGOT, 2, 1),
        ShopItem(1, null, Material.QUARTZ, 128, 1),
        ShopItem(1, null, Material.GLOWSTONE, 128, 1),
        ShopItem(1, null, Material.PRISMARINE_SHARD, 32, 1),

        ShopItem(1, null, Material.COBBLESTONE, 777, 1),
        ShopItem(1, null, Material.CLAY, 96, 1),
        ShopItem(1, null, Material.APPLE, 64, 1),
        ShopItem(1, null, Material.ROTTEN_FLESH, 128, 1),

        /**
         * Page 2
         */
        // ...
    )

    private val buttons: Array<ShopButton> = arrayOf(
        ShopButton(Named.Comp(
            Component.text("장비 강화").color(TextColor.color(0x00FF00))
                .decoration(TextDecoration.ITALIC, false)
        ), 4, Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, { event ->
            val player = event.whoClicked

            player.closeInventory()
            player.openInventory(ReinforceGui.inv)
        }),
    )

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        if (Example.IS_DEV) Example.instance.logger.info("Prevent click")
        if (event.inventory == inv) event.isCancelled = true
    }

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("상점"))

//    private var page = 1

    init {
        // Add buttons
//        for (button in buttons) {
//            inv.setItem(button.loc ?: -1, ItemStack(button.material).apply {
//                itemMeta = itemMeta.apply { displayName(button.showName) }
//            })
//        }

        // Add items
        var passed = 0

        for (i in items.indices) {
            if (passed == ITEMS_ON.size) break
            val item = items[i]
            val slot = ITEMS_ON[i]
            val itemStack = ItemStack(item.material).apply {
                itemMeta = itemMeta.apply {
                    lore(
                        mutableListOf(
                            Component.text("필요한 수: ${item.needs}").color(TextColor.color(0x00FF00))
                                .decoration(TextDecoration.ITALIC, false),
                            Component.text("확장권 수: ${item.amount}").color(TextColor.color(0x00FFFF))
                                .decoration(TextDecoration.ITALIC, false),
                        )
                    )
                }
            }
            inv.setItem(slot, itemStack)
            passed++
        }

        // Fill empty slots
        for (i in 0 until SIZEOF) {
            val emptyItem = ItemStack(Material.GRAY_STAINED_GLASS_PANE).apply {
                itemMeta = itemMeta.apply { displayName(Component.text(" ")) }
            }
            if (inv.getItem(i) == null) inv.setItem(i, emptyItem)
        }
    }

//    /**
//     * @unused
//     * Remove items by index in ITEMS_ON for new/next/prev page ...etc.
//     */
//    fun cleanIt() {
//        for (i in ITEMS_ON) inv.setItem(i, ItemStack(Material.AIR))
//    }

}