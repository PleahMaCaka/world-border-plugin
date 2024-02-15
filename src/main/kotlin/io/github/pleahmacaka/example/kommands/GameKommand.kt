package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.GameStatus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.plugin.java.JavaPlugin

var realStart: Boolean = false

fun gameKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("game") {
            then("start") {
                executes {
                    if (!realStart) {
                        sender.sendMessage("현재 지점을 시작점으로 설정하게 됩니다. 게임을 시작하시려면 다시 한번 명령어를 사용하세요.")
                        realStart = true
                        GameStatus.bar.name(Component.text("게임 시작을 위해 준비하고 있습니다..."))
                    } else {
                        sender.sendMessage(
                            Component.text("현재 지점 [0, 0, 0] 을 기준으로 게임을 시작합니다.").color(TextColor.color(0x00FF00))
                        )
                    }
                }
            }
            then("clean") {
                executes {
                    GameStatus.removeAll()
                    sender.sendMessage("플러그인 정보를 청소합니다. 설정된 구성을 초기화 하지 않습니다.")
                }
            }
            executes {
                sender.sendMessage("/game <start | clean>")
            }
        }
    }
}