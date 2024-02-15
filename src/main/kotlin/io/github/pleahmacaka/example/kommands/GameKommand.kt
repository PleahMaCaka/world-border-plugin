package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.GameStatus
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

var isReady: Boolean = false

fun gameKommand(plugin: JavaPlugin) {
    plugin.kommand {
        register("game") {
            then("start") {
                executes {
                    if (!isReady) {
                        sender.sendMessage("현재 지점을 시작점으로 설정하게 됩니다. 게임을 시작하시려면 다시 한번 명령어를 사용하세요.")
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

                        // place stronghold
//                        Bukkit.getServer().structureManager.createStructure().place(
//                            world.getHighestBlockAt(player.location).location,
//                            true,
//                            StructureRotation.NONE,
//                            Mirror.NONE,
//                            -1,
//                            Random().nextFloat(),
//                            Random()
//                        )
                        player.sendMessage("StrongHold 생성됨.")
                        Bukkit.dispatchCommand(player, "place structure minecraft:stronghold ~ 12 ~") // uwu

                        // ... do something take a long time ... //

                        GameStatus.setName("현재 월드보더 크기 : ${GameStatus.borderSize}")
                        player.sendMessage(
                            Component.text("현재 지점 [0, 0, 0] 을 기준으로 게임을 시작합니다.").color(TextColor.color(0x00FF00))
                        )
                    }
                }
            }
            then("bar") {
                then("show") {
                    executes {
                        GameStatus.showAll()
                        sender.sendMessage("게임 바를 표시합니다.")
                    }
                }
                then("hide") {
                    executes {
                        GameStatus.hideAll()
                        sender.sendMessage("게임 바를 숨깁니다.")
                    }
                }
                executes { sender.sendMessage("/game bar <show | hide>") }
            }
            then("clean") {
                executes {
                    sender.sendMessage("플러그인 정보를 청소합니다. 설정된 구성을 초기화 하지 않습니다.")
                }
            }
            executes {
                sender.sendMessage("/game <start | bar | clean>")
            }
        }
    }
}