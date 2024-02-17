package io.github.pleahmacaka.example.gui

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

object RewardGui {

    private const val SIZEOF = 6 * 9

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("보상 목록"))

    fun getRewardSize(): Int {
        return inv.contents.filter { it != null && it.type != org.bukkit.Material.AIR }.size
    }
}