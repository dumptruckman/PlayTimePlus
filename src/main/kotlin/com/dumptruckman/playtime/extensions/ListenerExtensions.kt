package com.dumptruckman.playtime.extensions

import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

fun Listener.registerEvents(plugin: Plugin) {
    plugin.server.pluginManager.registerEvents(this, plugin)
}