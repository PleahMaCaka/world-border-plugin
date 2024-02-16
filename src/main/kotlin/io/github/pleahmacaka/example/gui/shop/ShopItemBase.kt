package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import org.bukkit.Material

open class ShopItemBase(
    val page: Int, // Page number, -1 for pin it

    /**
     * Not used as it is, use @displayName for display name.
     */
    private var name: Named? = null,

    val material: Material,

    private var lore: Array<Component>? = null,

    /**
     * constant index location for exchange slot
     */
    var loc: Int? = null
) {
    private var displayName: Component? = null

    init {
        displayName = when (name) {
            is Named.Text -> Component.text((name as Named.Text).text)
            is Named.Comp -> (name as Named.Comp).component
            else -> Component.text(material.name)
        }
    }
}