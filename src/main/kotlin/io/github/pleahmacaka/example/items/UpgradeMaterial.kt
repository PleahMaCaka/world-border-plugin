package io.github.pleahmacaka.example.items

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object UpgradeMaterial {

    val itemStack = ItemStack(Material.ARCHER_POTTERY_SHERD).apply {
        itemMeta = itemMeta.apply {
            displayName(
                Component.text("강화 재료")
                    .color(TextColor.color(0x00FF00))
                    .decoration(TextDecoration.ITALIC, false)
            )
        }
    }

}