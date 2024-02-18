package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.gui.shop.ShopGui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin

val randSent: List<String> = listOf(
    "거지는 들어올 수 없습니다.",
    "사지도 않을 물건을 건들지 마십시오.",
    "네더라이트가 없으면 상점에 들어올 수 없습니다. (아님)",
    "짜잔",
    "여기에 헛소리 적기 (나중에 지우기)"
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
                player.playSound(entity.location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1f, 1f)
            }
        }
    }
}