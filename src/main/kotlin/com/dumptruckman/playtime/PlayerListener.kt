package com.dumptruckman.playtime

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerListener(private val plugin: PlayTimePlus) : Listener {

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        plugin.sessionTimes.put(event.player.uniqueId, System.currentTimeMillis())
    }

    @EventHandler
    fun playerQuit(event: PlayerQuitEvent) {
        plugin.sessionTimes.remove(event.player.uniqueId)
    }
}