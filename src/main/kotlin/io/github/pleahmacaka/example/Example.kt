package io.github.pleahmacaka.example

import io.github.pleahmacaka.example.events.*
import io.github.pleahmacaka.example.gui.RewardGui
import io.github.pleahmacaka.example.gui.shop.ShopGui
import io.github.pleahmacaka.example.items.ExpandBorderItem
import io.github.pleahmacaka.example.kommands.cleanKommand
import io.github.pleahmacaka.example.kommands.gameKommand
import io.github.pleahmacaka.example.kommands.shopKommand
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class Example : JavaPlugin() {

    companion object {
        lateinit var instance: Example
            private set
        const val IS_DEV = true
    }

    override fun onEnable() {
        instance = this
        logger.info("WBS Plugin Enabled!")

        if (!GameManager.started)
            GameManager.bossbar.name(Component.text("월드보더 서바이벌 V0 작동중"))
        else {
            GameManager.setBossbar("현재 월드보더 크기 : ${instance.server.getWorld("world")?.worldBorder?.size?.toInt()}")
            GameManager.showBossbarAll()
        }

        RewardGui.loadReward()
        GameManager.loadConfig()

        registerKommands()
        registerListeners()
    }

    override fun onDisable() {
        GameManager.hideBossbarAll() // 한번 인스턴스를 잃어버리면 다시 핸들링 불가능함

        RewardGui.saveReward()
        GameManager.saveConfig()
        logger.info("WBs Plugin Disabled!")
    }

    private fun registerKommands() {
        shopKommand(this)
        gameKommand(this)
        cleanKommand(this)
    }

    private fun registerListeners() {
        listOf(
            ShopGui,
            ExpandBorderItem,
            ReplaceEnchantTable,
            AutoSaveRewards,
            ShowBossBar,
            CheckJoinedWorld,
            RespawnToShowBossBar
        ).forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

}
