package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import org.bukkit.plugin.java.JavaPlugin

fun cleanKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("clean") {
            executes {
                for (i in 0 until 100) {
                    player.sendMessage("")
                }
            }
        }
    }
}