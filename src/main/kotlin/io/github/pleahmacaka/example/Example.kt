package io.github.pleahmacaka.example

import io.github.pleahmacaka.example.gui.shop.ShopGui
import io.github.pleahmacaka.example.kommands.shopKommand
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

        registerKommands()
        registerListeners()
    }

    override fun onDisable() {
        logger.info("Example Plugin Disabled!")
    }

    private fun registerKommands() {
        shopKommand(this)
    }

    private fun registerListeners() {
        server.pluginManager.registerEvents(ShopGui, this)
    }

}
