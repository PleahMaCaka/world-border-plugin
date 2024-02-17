package io.github.pleahmacaka.example.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

object ReinforceGui {

    private const val SIZEOF = 3 * 9

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("강화"))
}