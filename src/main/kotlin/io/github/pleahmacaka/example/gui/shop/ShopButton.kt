package io.github.pleahmacaka.example.gui.shop

import io.github.pleahmacaka.example.utils.Named
import net.kyori.adventure.text.Component
import org.bukkit.Material

class ShopButton(
    name: Named,
    page: Int,
    loc: Int,
    material: Material,
    lore: Array<Component>
) : ShopItemBase(
    page = page, name = name, material = material, lore = lore, loc = loc
)