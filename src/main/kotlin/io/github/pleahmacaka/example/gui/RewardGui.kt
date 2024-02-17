package io.github.pleahmacaka.example.gui

import io.github.pleahmacaka.example.Example
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import java.io.File

object RewardGui {

    private const val SIZEOF = 6 * 9

    val inv: Inventory = Bukkit.createInventory(null, SIZEOF, Component.text("보상 목록"))

    fun getRewardSize(): Int {
        return inv.contents.filter { it != null && it.type != org.bukkit.Material.AIR }.size
    }

    fun saveReward() {
        val file = File("./config/rewards.yaml")
        file.createNewFile()
        val config = YamlConfiguration.loadConfiguration(file)
        for (i in 0 until SIZEOF) {
            val item = inv.getItem(i)
            if (item != null) {
                config.set("rewards.$i", item)
            }
        }
        config.save(file)
    }

    fun loadReward() {
        val file = File("./config/rewards.yaml")
        if (!file.exists()) {
            Example.instance.logger.info("보상 데이터를 찾을 수 없습니다.")
            return
        }
        val config = YamlConfiguration.loadConfiguration(file)
        for (i in 0 until SIZEOF) {
            val item = config.getItemStack("rewards.$i")
            if (item != null) {
                inv.setItem(i, item)
            }
        }
    }
}