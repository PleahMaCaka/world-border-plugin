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

    val configFile = File("./config/gamestatus.yaml")

    val config = YamlConfiguration.loadConfiguration(configFile)

    init {
        if (!configFile.exists()) configFile.createNewFile()

        if (!config.contains("started")) {
            config.set("started", false)
            config.save(configFile)
        }
    }


    /**
     * Configurations
     */

    var started = config.getBoolean("started")
    var borderSize = 8


    /**
     * Bossbar
     */

    var bar = BossBar.bossBar(
        Component.text("대기중"), 1.0f, BossBar.Color.BLUE, Overlay.PROGRESS
    )

    fun setBossbar(name: String) {
        bar.name(Component.text(name))
    }

    fun showBossbarAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.addViewer(player as Audience)
        }
    }

    fun hideBossbarAll() {
        (Bukkit.getOnlinePlayers() as MutableList<*>).forEach { player ->
            bar.removeViewer(player as Audience)
        }
    }


    /**
     * Read/Writes Configurations
     */

    fun getConfig(k: String): String? {
        return config.getString(k)
    }

    fun setConfig(k: String, v: Any) {
        config.set(k, v)
        config.save(configFile)
    }

    fun saveConfig() {
        config.save(configFile)
    }

    fun loadConfig() {
        config.load(configFile)
    }

}