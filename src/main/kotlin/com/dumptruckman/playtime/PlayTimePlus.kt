package com.dumptruckman.playtime

import com.dumptruckman.playtime.extensions.registerEvents
import com.dumptruckman.playtime.util.Logging
import com.dumptruckman.springjdbc.ConnectionInfo
import com.dumptruckman.springjdbc.SpringJdbcAgent
import org.apache.commons.lang.IllegalClassException
import org.bstats.Metrics
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.UUID

class PlayTimePlus : JavaPlugin() {

    internal val sessionTimes = mutableMapOf<UUID, Long>()
    internal val jdc by lazy {
        config.get("database") as? ConnectionInfo ?: throw IllegalClassException("")
        SpringJdbcAgent.createAgent()
    }

    override fun onLoad() {
        Logging.init(this)
    }

    override fun onEnable() {
        initializeConfig()
        initializeDatabase()

        PlayerListener(this).registerEvents(this)

        Metrics(this)
    }

    private fun initializeDatabase() {

    }

    private fun initializeConfig() {
        if (!File(dataFolder, "config.yml").exists()) {
            saveDefaultConfig()
        }
        config.defaults.addDefaults(YamlConfiguration.loadConfiguration(getTextResource("config.yml")))
    }

    /**
     * Returns the time played in the player's current session.
     */
    fun getSessionTime(player: OfflinePlayer): Long {
        val time = sessionTimes.getOrDefault(player.uniqueId, 0L)
        return if (time > 0) {
            System.currentTimeMillis() - time
        } else {
            0L
        }
    }

    companion object {
        init {
            @Suppress("UNCHECKED_CAST")
            ConfigurationSerialization.registerClass(ConnectionInfo::class.java as Class<out ConfigurationSerializable>,
                    "ConnectionInfo")
        }
    }
}