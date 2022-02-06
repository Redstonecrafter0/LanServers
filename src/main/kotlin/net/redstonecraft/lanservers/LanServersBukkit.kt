package net.redstonecraft.lanservers

import org.bukkit.plugin.java.JavaPlugin

class LanServersBukkit: JavaPlugin(), LanServersPlugin {

    override val port: Int
        get() = server.port
    override val motd: String
        get() = server.motd

    override fun onEnable() = enable()
    override fun onDisable() = disable()
}
