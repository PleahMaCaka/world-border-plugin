package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.gui.ReinforceGui
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin

fun reinforceKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("reinforce") {
            executes {
                player.playSound(entity.location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1f, 1f)
                player.openInventory(ReinforceGui.inv)
            }
        }
    }
}