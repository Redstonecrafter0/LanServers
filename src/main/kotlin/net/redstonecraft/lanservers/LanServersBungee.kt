package net.redstonecraft.lanservers

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class LanServersBungee: Plugin(), LanServersPlugin {

    override val port: Int
        get() = ProxyServer.getInstance().config.listeners.first().host.port
    override val motd: String
        get() = ProxyServer.getInstance().config.listeners.first().motd

    override fun onEnable() = enable()
    override fun onDisable() = disable()
}
