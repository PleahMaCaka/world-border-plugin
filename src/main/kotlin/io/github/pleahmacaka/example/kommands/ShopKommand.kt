package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.gui.shop.ShopGui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.plugin.java.JavaPlugin

val randSent: List<String> = listOf(
    "돈좀 있으신가봐요?",
    "돈 없으면 나가세요.",
    "돈이 없으면 상점에 들어올 수 없습니다. (아님)"
)

fun shopKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("shop") {
            requires { isPlayer }
            executes {
                player.sendMessage(
                    Component.text("상점을 열었습니다! - " + randSent[randSent.indices.random()])
                        .color(TextColor.color(0x00FF00))
                )
                player.openInventory(ShopGui.inv)
            }
        }
    }
}