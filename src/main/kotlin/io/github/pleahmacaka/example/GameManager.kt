package io.github.pleahmacaka.example

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.bossbar.BossBar.Overlay
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object GameManager {

    /**
     * FS Handler
     */

    private val configFile = File("./config/gamestatus.yaml")

    val config = YamlConfiguration.loadConfiguration(configFile)

    init {
        if (!configFile.exists()) configFile.createNewFile()

        val defaultConfigPairs = mapOf(
            "started" to false,
            "startedAt" to listOf(0, 0, 0),
            "initBorderSize" to 16,
            "expandingCount" to 0,
            "expanderMultiplier" to 1,
            "enchantMultiplier" to 7,
            "netherJoined" to false,
            "endJoined" to false
        )

        defaultConfigPairs.forEach { (key, value) ->
            if (!config.contains(key)) {
                config.set(key, value)
                config.save(configFile)
            }
        }

        reloadConfig(true)
    }


    /**
     * Configurations
     */

    var started: Boolean
        get() = config.getBoolean("started")
        set(value) = setConfig("started", value)

    var startedAt: List<Int>
        get() = config.getIntegerList("startedAt")
        set(value) = setConfig("startedAt", value)

    var initBorderSize: Int
        get() = config.getInt("initBorderSize")
        set(value) = setConfig("initBorderSize", value)

    var expandingCount: Int
        get() = config.getInt("expandingCount")
        set(value) = setConfig("expandingCount", value)

    var expanderMultiplier: Int
        get() = config.getInt("expanderMultiplier")
        set(value) = setConfig("expanderMultiplier", value)

    var enchantMultiplier: Int
        get() = config.getInt("enchantMultiplier")
        set(value) = setConfig("enchantMultiplier", value)

    var netherJoined: Boolean
        get() = config.getBoolean("netherJoined")
        set(value) = setConfig("netherJoined", value)

    var endJoined: Boolean
        get() = config.getBoolean("endJoined")
        set(value) = setConfig("endJoined", value)

    /**
     * Bossbar
     */

    var bossbar = BossBar.bossBar(
        Component.text("대기중"), 1.0f, BossBar.Color.BLUE, Overlay.PROGRESS
    )

    fun setBossbar(name: String) {
        bossbar.name(Component.text(name))
    }

    fun showBossbarAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bossbar.addViewer(player as Audience)
        }
    }

    fun hideBossbarAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bossbar.removeViewer(player as Audience)
        }
    }


    /**
     * Read/Writes Configurations
     */

    private fun setConfig(k: String, v: Any) {
        config.set(k, v)
        saveConfig(true)
    }

    fun saveConfig(nolog: Boolean = false) {
        if (!nolog) Example.instance.logger.info("Configurations saved.")
        config.save(configFile)
    }

    fun loadConfig(nolog: Boolean = false) {
        if (!nolog) Example.instance.logger.info("Configurations loaded.")
        config.load(configFile)
    }

    fun reloadConfig(saveFirst: Boolean = false) {
        if (saveFirst) saveConfig(true)
        loadConfig(true)
        saveConfig(true)
        Example.instance.logger.info("Configurations reloaded.")
    }

}