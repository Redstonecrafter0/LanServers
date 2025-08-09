package net.redstonecraft.lanservers;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

import java.io.IOException;


@Plugin(
    id = "lanservers",
    name = "LanServers",
    version = "2.0.0",
    description = "Lists the Server on the Multiplayer list as LanServer if on the same network.",
    url = "https://github.com/Redstonecrafter0/LanServers",
    authors = {
        "Redstonecrafter0"
    }
)
public class LanServersVelocity implements ILanServersPlugin {

    private final ProxyServer server;
    private LanServers lanServers = null;

    @Inject
    LanServersVelocity(ProxyServer server, Logger logger) {
        this.server = server;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        if (lanServers == null) {
            try {
                lanServers = new LanServers(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lanServers.enable();
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        lanServers.disable();
    }

    @Override
    public int getPort() {
        return server.getBoundAddress().getPort();
    }

    @Override
    public String getMotd() {
        return LegacyComponentSerializer.legacySection().serialize(server.getConfiguration().getMotd());
    }

}
