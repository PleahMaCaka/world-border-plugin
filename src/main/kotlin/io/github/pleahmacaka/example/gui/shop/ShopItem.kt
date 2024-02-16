package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import org.bukkit.Material

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
)