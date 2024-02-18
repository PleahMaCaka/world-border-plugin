package io.github.pleahmacaka.example.kommands

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import io.github.pleahmacaka.example.GameManager
import io.github.pleahmacaka.example.gui.RewardGui
import io.github.pleahmacaka.example.items.ExpandBorderItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import org.joml.Vector3i

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
                        GameManager.setBossbar("게임 시작을 위해 준비하고 있습니다...")
                        GameManager.started = true

                        val world = player.world
                        val highestBlock: Location = world.getHighestBlockAt(player.location).location

                        // set default world spawn location
                        world.spawnLocation = highestBlock

                        // initialize a world border
                        world.worldBorder.center = world.spawnLocation
                        world.worldBorder.size = GameManager.borderSize.toDouble()

                        Bukkit.dispatchCommand(player, "place structure minecraft:stronghold ~100 12 ~100") // uwu
                        player.sendMessage("StrongHold 생성됨.")

                        // ... do something take a long time ... //

                        GameManager.setBossbar("현재 월드보더 크기 : ${GameManager.borderSize}")

                        val hPos = Vector3i(
                            highestBlock.x.toInt(), highestBlock.y.toInt(), highestBlock.z.toInt()
                        )

                        GameManager.started = true
                        GameManager.startedAt = listOf(hPos.x, hPos.y, hPos.z)

                        player.sendMessage(
                            Component.text("현재 지점 [${hPos.x}, ${hPos.y}, ${hPos.z}] 을 기준으로 게임을 시작합니다.")
                                .color(TextColor.color(0x00FF00))
                        )
                    }
                }
            }
            then("bar") {
                then("show") {
                    executes {
                        GameManager.showBossbarAll()
                        player.sendMessage("게임 바를 표시합니다.")
                    }
                }
                then("hide") {
                    executes {
                        GameManager.hideBossbarAll()
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
                then("expanderMultiplier") {
                    then("multiplier" to int()) {
                        executes {
                            val multiplier: Int by it
                            GameManager.expanderMultiplier = multiplier
                            player.sendMessage("월드 경계 확장기의 배수가 ${multiplier}(으)로 설정되었습니다.")
                        }
                    }
                    executes { player.sendMessage("/game config expanderMultiplier <number>") }
                }
                then("load") {
                    executes {
                        GameManager.loadConfig()
                        player.sendMessage("게임 설정을 불러왔습니다.")
                    }
                }
                then("reward") {
                    then("load") {
                        executes {
                            RewardGui.loadReward()
                            player.sendMessage("게임 보상을 불러왔습니다.")
                        }
                    }
                    executes {
                        player.openInventory(RewardGui.inv)
                        player.sendMessage("게임 보상 설정을 엽니다.")
                    }
                }
                executes { player.sendMessage("/game config <reward>") }
            }
            executes { player.sendMessage("/game <start | give | config | bar>") }
        }
    }
}