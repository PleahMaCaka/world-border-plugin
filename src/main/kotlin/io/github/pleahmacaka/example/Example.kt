package io.github.pleahmacaka.example

import io.github.pleahmacaka.example.gui.shop.ShopGui
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
        logger.info("Example Plugin Enabled!")

        GameStatus.bar.name(Component.text("애플의 월드보더 서바이벌 V0 작동중"))
        GameStatus.showAll()
        registerKommands()
        registerListeners()
    }

    override fun onDisable() {
        GameStatus.hideAll() // 한번 인스턴스를 잃어버리면 다시 핸들링 불가능함
        logger.info("Example Plugin Disabled!")
    }

    private fun registerKommands() {
        shopKommand(this)
        gameKommand(this)
    }

    private fun registerListeners() {
        server.pluginManager.registerEvents(ShopGui, this)
    }

}
