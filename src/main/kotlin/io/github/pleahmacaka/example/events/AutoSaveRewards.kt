package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.gui.RewardGui
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

object AutoSaveRewards : Listener {

    @EventHandler
    fun saveReward(event: InventoryCloseEvent) {
        if (event.inventory != RewardGui.inv) return
        RewardGui.saveReward()
        event.player.sendMessage("랜덤 보상이 저장되었습니다.")
    }

}