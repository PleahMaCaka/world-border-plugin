package io.github.pleahmacaka.example.gui

import io.github.pleahmacaka.example.items.UpgradeMaterial
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object ReinforceGui : Listener {

    private const val SIZEOF = 3 * 9

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("강화"))

    private val emptyItem = createItem(Material.GRAY_STAINED_GLASS_PANE, " ")
    private val yesBtn = createItem(Material.GREEN_STAINED_GLASS_PANE, "강화하기", TextColor.color(0x00FF00), false)

    init {
        inv.setItem(14 + 9, yesBtn)
        fillEmptySlots()
    }

    private fun createItem(material: Material, name: String, color: TextColor? = null, italic: Boolean? = null) =
        ItemStack(material).apply {
            itemMeta = itemMeta.apply {
                displayName(
                    Component.text(name).color(color ?: TextColor.color(0xFFFFFF))
                        .decoration(TextDecoration.ITALIC, italic ?: true)
                )
            }
        }

    private fun fillEmptySlots() {
        for (i in 0 until SIZEOF) {
            if (i in listOf(12, 14)) continue
            if (inv.getItem(i)?.type == null) inv.setItem(i, emptyItem)
        }
    }

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        validCheckInv(event.inventory) ?: return

        val player = event.whoClicked

        when (event.currentItem) {
            emptyItem -> event.isCancelled = true
            yesBtn -> reinforce(event, player)
        }
    }

    private fun reinforce(event: InventoryClickEvent, player: HumanEntity) {
        if (inv.getItem(12) == null || inv.getItem(14) == null) {
            player.sendMessage("강화할 장비 또는 강화 재료를 넣어주세요.")
            event.isCancelled = true
            return
        }

        if (!inv.getItem(12)?.isSimilar(UpgradeMaterial.itemStack)!!) {
            player.sendMessage("강화 재료 칸에 다른 아이템이 있습니다.")
            event.isCancelled = true
            return
        }

        event.isCancelled = true
        inv.getItem(12)?.updateInventorySlot(12)
        player.sendMessage("강화된 장비: ${inv.getItem(14)?.type?.name}")
    }

    // Simplifies updating an inventory slot and cares for zero amounts
    private fun ItemStack.updateInventorySlot(slot: Int) {
        this.amount--
        inv.setItem(slot, takeIf { amount > 0 } ?: ItemStack(Material.AIR))
    }

    @EventHandler
    fun onInvClose(event: InventoryCloseEvent) {
        validCheckInv(event.inventory) ?: return
        inv.setItem(14 + 9, yesBtn)

        val player = event.player

        for (i in listOf(12, 14)) {
            val item = inv.getItem(i)
            if (item != null) {
                player.transferItem(item, i)
            }
        }
    }

    private fun validCheckInv(givenInventory: Inventory): Inventory? = if (givenInventory != inv) null else inv

    // Consolidate how items are transferred to player
    private fun HumanEntity.transferItem(item: ItemStack, slot: Int) {
        if (this.inventory.firstEmpty() != -1) {
            this.inventory.addItem(item)
        } else {
            this.world.dropItem(this.location, item)
        }
        inv.setItem(slot, ItemStack(Material.AIR))
    }
}