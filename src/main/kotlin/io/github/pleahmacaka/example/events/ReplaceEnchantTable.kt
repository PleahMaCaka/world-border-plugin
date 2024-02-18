package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.gui.ReinforceGui
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object ReplaceEnchantTable : Listener {

    // if that block is enchant table, close it to open ReinforceGui.inv
    @EventHandler
    fun openReinforce(event: PlayerInteractEvent) {
        if (event.clickedBlock?.type == Material.ENCHANTING_TABLE
            && event.action.isRightClick
        ) {
            event.isCancelled = true
            event.player.openInventory(ReinforceGui.inv)
        }
    }

}