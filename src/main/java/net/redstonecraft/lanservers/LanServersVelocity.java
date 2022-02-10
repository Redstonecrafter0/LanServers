package net.redstonecraft.lanservers;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

@Plugin(
        id = "lanservers",
        name = "LanServers",
        version = "1.0.0",
        description = "Lists the Server on the Multiplayer list as LanServer if on the same network.",
        url = "https://github.com/Redstonecrafter0/LanServers",
        authors = {"Redstonecrafter0"}
)
public class LanServersVelocity implements LanServersPlugin {

    private final ProxyServer proxyServer;

    public LanServersVelocity(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
    }

    @Override
    public int getPort() {
        return proxyServer.getBoundAddress().getPort();
    }

    @Override
    public String getMotd() {
        return LegacyComponentSerializer.legacySection().serialize(proxyServer.getConfiguration().getMotd());
    }

}
