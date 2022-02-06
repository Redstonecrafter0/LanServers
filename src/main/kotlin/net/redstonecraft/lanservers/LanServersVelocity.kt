package net.redstonecraft.lanservers

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.slf4j.Logger

@Plugin(
    id = "lanservers",
    name = "LanServers",
    version = "1.0.0",
    description = "Lists the Server on the Multiplayer list as LanServer if on the same network.",
    url = "https://github.com/Redstonecrafter0/LanServers",
    authors = ["Redstonecrafter0"]
)
class LanServersVelocity @Inject constructor(private val server: ProxyServer, private val logger: Logger): LanServersPlugin {

    override val port: Int
        get() = server.boundAddress.port
    override val motd: String
        get() = LegacyComponentSerializer.legacySection().serialize(server.configuration.motd)

    @Subscribe
    fun onInit(event: ProxyInitializeEvent) = enable()

    @Subscribe
    fun onDisable(event: ProxyShutdownEvent) = disable()
}
