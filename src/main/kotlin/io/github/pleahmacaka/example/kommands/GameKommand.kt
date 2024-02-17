package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.GameStatus
import io.github.pleahmacaka.example.gui.RewardGui
import io.github.pleahmacaka.example.items.ExpandBorderItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.math.abs

var isReady: Boolean = false

fun gameKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("game") {
            requires { player.isOp }
            then("start") {
                executes {
                    if (!isReady) {
                        player.sendMessage("현재 지점을 시작점으로 설정하게 됩니다. 게임을 시작하시려면 다시 한번 명령어를 사용하세요.")
                        isReady = true
                    } else {
                        GameStatus.setName("게임 시작을 위해 준비하고 있습니다...")
                        GameStatus.started = true

                        val world = player.world

                        // set default world spawn location
                        world.spawnLocation = world.getHighestBlockAt(player.location).location

                        // initialize a world border
                        world.worldBorder.center = world.spawnLocation
                        world.worldBorder.size = GameStatus.borderSize.toDouble()

                        player.sendMessage("StrongHold 생성됨.")
                        Bukkit.dispatchCommand(player, "place structure minecraft:stronghold ~100 12 ~100") // uwu

                        // ... do something take a long time ... //

                        GameStatus.setName("현재 월드보더 크기 : ${GameStatus.borderSize}")

                        val ploc = player.location
                        player.sendMessage(
                            Component.text("현재 지점 [${abs(ploc.x)}, ${abs(ploc.y)}, ${abs(ploc.z)}] 을 기준으로 게임을 시작합니다.")
                                .color(TextColor.color(0x00FF00))
                        )
                    }
                }
            }
            then("bar") {
                then("show") {
                    executes {
                        GameStatus.showAll()
                        player.sendMessage("게임 바를 표시합니다.")
                    }
                }
                then("hide") {
                    executes {
                        GameStatus.hideAll()
                        player.sendMessage("게임 바를 숨깁니다.")
                    }
                }
                executes { player.sendMessage("/game bar <show | hide>") }
            }
            then("give") {
                then("expander") {
                    then("count" to int()) {
                        executes {
                            val count: Int by it
                            for (i in 1..count) player.inventory.addItem(ExpandBorderItem.itemStack)
                            player.sendMessage("월드 경계 확장기를 ${count}개 지급합니다.")
                            return@executes
                        }
                    }
                    executes {
                        player.inventory.addItem(ExpandBorderItem.itemStack)
                        player.sendMessage("월드 경계 확장기를 지급합니다.")
                    }
                }
                executes { player.sendMessage("/game give <expander>") }
            }
            then("config") {
                then("reward") {
                    executes {
                        player.openInventory(RewardGui.inv)
                        player.sendMessage("게임 보상 설정을 엽니다.")
                    }
                }
                executes {
                    player.sendMessage("/game config <reward>")
                }
            }
            then("clean") {
                executes {
                    player.sendMessage("플러그인 정보를 청소합니다. 설정된 구성을 초기화 하지 않습니다.")
                }
            }
            executes {
                player.sendMessage("/game <start | bar | clean>")
            }
        }
    }
}