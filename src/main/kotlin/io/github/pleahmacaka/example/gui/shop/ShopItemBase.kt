package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.event.Listener

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
    open var loc: Int? = null
) : Listener {
    var showName: Component? = null

    init {
        showName = when (name) {
            is Named.Text -> Component.text((name as Named.Text).text)
            is Named.Comp -> (name as Named.Comp).component
            else -> Component.text(material.name)
        }
    }
}