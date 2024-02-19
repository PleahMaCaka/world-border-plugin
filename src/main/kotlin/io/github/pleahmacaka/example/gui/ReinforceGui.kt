package io.github.pleahmacaka.example.gui

import io.github.pleahmacaka.example.GameManager
import io.github.pleahmacaka.example.items.UpgradeMaterial
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.min

object ReinforceGui : Listener {

//    val PREFIXES = listOf(
//        "어둠의 힘이 깃든",
//        "잠든 수호자의",
//        "멋쟁이 토마토의",
//        "빛나는",
//        "사과맛",
//        "넘볼 수 없는",
//        "무시무시한",
//        "무적의",
//        "대단히 엄청난",
//        "치과에 온듯한",
//        "무서운",
//        "귀여운",
//        "아름다운",
//        "귀족의",
//        "평범한",
//        "평범하지 않은",
//        "너드의",
//        "영웅의",
//        "도둑의",
//        "쓸 데 없는",
//        "큰",
//        "작은",
//        "지독한",
//        "사윗감"
//    )

    private const val SIZEOF = 3 * 9

    private const val successPer = 90 // will be decreased by 10% every time
    private const val failPer = 10     // will be increased by 10% every time
    private const val destroyPer = 1  // will be increased by 2% every time

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
    fun openReinforce(event: InventoryClickEvent) {
        validCheckInv(event.inventory) ?: return

        val player = event.whoClicked as Player

        when (event.currentItem) {
            emptyItem -> event.isCancelled = true
            yesBtn -> reinforce(event, player)
        }
    }

    private fun Material.getEnchantments(): List<Enchantment> {
        val applicable = mutableListOf<Enchantment>()
        for (enchantment in Enchantment.values()) {
            if (enchantment.itemTarget.includes(this)) {
                applicable.add(enchantment)
            }
        }
        return applicable
    }

    private fun reinforce(event: InventoryClickEvent, player: Player) {
        if (inv.getItem(12) == null || inv.getItem(14) == null) {
            player.sendMessage("강화할 장비 또는 강화 재료를 넣어주세요.")
            player.playSound(player.location, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 3f, 1f)
            event.isCancelled = true
            return
        }

        if (!inv.getItem(12)?.isSimilar(UpgradeMaterial.itemStack)!!) {
            player.sendMessage("강화 재료 칸에 다른 아이템이 있습니다.")
            player.playSound(player.location, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 3f, 1f)
            event.isCancelled = true
            return
        }

        event.isCancelled = true
        inv.getItem(12)?.updateInventorySlot(12) // it removes material

        val originItem = inv.getItem(14) ?: return

        var first = false

        if (originItem.lore() == null) {
            originItem.lore(
                mutableListOf(
                    Component.text("★☆☆☆☆☆☆☆☆☆").color(TextColor.color(0xFFFF00))
                )
            )
            first = true
        }

        val lore = originItem.lore()!![0] as TextComponent
        val filledStar = lore.content().count { it == '★' } + 1

        if (filledStar == 11) {
            player.sendMessage("더 이상 강화할 수 없습니다.")
            player.closeInventory()
            player.inventory.addItem(UpgradeMaterial.itemStack)
            return
        }

        val emptyStar = 10 - filledStar
        val emptyStarStr = "☆".repeat(emptyStar)
        if (!first) originItem.lore(
            mutableListOf(
                Component.text("★".repeat(filledStar) + emptyStarStr).color(TextColor.color(0xFFFF00))
            )
        )

        player.playSound(player.location, Sound.BLOCK_AMETHYST_BLOCK_BREAK, 3f, 1f)

        val rnd = Random()
        val randPercent = rnd.nextInt(100) + 1  // generates a random percentage between 1-100

        val successPerNow = successPer - filledStar * 10
        val failPerNow = (failPer + filledStar * 5) / 4
        val destroyPerNow = (destroyPer + filledStar * 2) / 4

        if (randPercent <= successPerNow) {
            val randLevel = filledStar / 2 + rnd.nextInt(GameManager.enchantMultiplier - (filledStar / 2) + 1)
            val availableEnchants = originItem.type.getEnchantments()

            if (availableEnchants.isNotEmpty()) {
                val maxTries = 100 // set this to a reasonable number based on your needs
                var validEnchantFound = false
                var tryCount = 0
                var rndEnchantment: Enchantment? = null

                while (!validEnchantFound) {
                    // Increase the tryCount and check if it exceeds maxTries
                    tryCount++
                    if (tryCount > maxTries) {
                        break
                    }

                    // Try loop until we find a valid enchantment
                    rndEnchantment = availableEnchants[rnd.nextInt(availableEnchants.size)]
                    if (!canEnchantItem(originItem, rndEnchantment)) continue
                    validEnchantFound = true
                }

                // Add the enchantment to the item
                if (validEnchantFound && rndEnchantment != null) {
                    // We calculate the level to upgrade based on randLevel
                    val currentEnchantmentLevel = if (originItem.containsEnchantment(rndEnchantment)) {
                        min(originItem.getEnchantmentLevel(rndEnchantment) + randLevel, rndEnchantment.maxLevel)
                    } else {
                        randLevel
                    }
                    originItem.addUnsafeEnchantment(rndEnchantment, currentEnchantmentLevel)
                }
            } else {
                player.sendMessage("강화할 수 없는 아이템입니다. 특별히 별은 달아드릴게요.")
                player.playSound(player.location, Sound.BLOCK_ANVIL_DESTROY, 3f, 3f)
                player.inventory.addItem(UpgradeMaterial.itemStack)
                return player.closeInventory()
            }

        } else if (randPercent <= successPerNow + failPerNow) {
            player.sendMessage("강화 실패!")
            player.playSound(player.location, Sound.BLOCK_SMITHING_TABLE_USE, 3f, 10f)
        } else if (randPercent <= successPerNow + failPerNow + destroyPerNow) {
            player.sendMessage("아이템이 파괴되었습니다!")
            player.playSound(player.location, Sound.BLOCK_ANVIL_DESTROY, 3f, 3f)
            inv.remove(originItem)
        }
    }

    private fun canEnchantItem(item: ItemStack, enchant: Enchantment): Boolean {
        return item.type.getEnchantments().contains(enchant) &&
                (item.getEnchantmentLevel(enchant) + 1 <= enchant.maxLevel)
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