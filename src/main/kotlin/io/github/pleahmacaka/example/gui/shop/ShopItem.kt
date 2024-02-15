package io.github.pleahmacaka.example.gui.shop

import org.bukkit.Material

@Suppress("unused")
data class ShopItem(
    val page: Int,              // page numbers
    val material: Material,     // display material
    val name: String? = null,   // display name, null for default

    var needs: Int,             // require items for exchange
    var amount: Int,            // get amount of expander

    /**
     * Allowed tags for exchange, null for only material
     */
    val allowList: MutableList<Material>? = null,

    /**
     * constant index location for exchange slot
     */
    var loc: Int? = null
)