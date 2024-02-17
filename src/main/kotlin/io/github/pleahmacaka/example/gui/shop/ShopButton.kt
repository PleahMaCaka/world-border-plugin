package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.Example
import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent

class ShopButton(
    name: Named,
    override var loc: Int? = null,
    material: Material,
    val action: (event: InventoryClickEvent) -> Unit,
    lore: Array<Component>? = null
) : ShopItemBase(
    page = -1, name = name, material = material, lore = lore, loc = loc
) {

    init {
        if (loc == null || loc!! < 0 || loc!! >= 54)
            throw IllegalArgumentException("Invalid index: $loc")

        if (Example.IS_DEV)
            Example.instance.logger.info("ShopButton.init: $name")

        Example.instance.server.pluginManager.registerEvents(this, Example.instance)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.inventory == ShopGui.inv) {
            if (Example.IS_DEV)
                Example.instance.logger.info("ShopButton.onClick: ${(this.showName as TextComponent).content()}") // wtf
            if (event.slot == loc)
                action(event)
        }
    }

}