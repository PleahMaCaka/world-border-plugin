package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.gui.shop.ShopGui
import org.bukkit.plugin.java.JavaPlugin

fun shopKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("shop") {
            requires { isPlayer }
            executes {
                player.openInventory(ShopGui.inv)
            }
        }
    }
}