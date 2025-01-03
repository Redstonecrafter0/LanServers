package net.redstonecraft.lanservers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;

public class LanServersBungee extends Plugin implements ILanServersPlugin {

    private LanServers lanServers = null;

    @Override
    public void onEnable() {
        if (lanServers == null) {
            try {
                lanServers = new LanServers(this);
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        }
        lanServers.enable();
    }

    @Override
    public void onDisable() {
        lanServers.disable();
    }

    @Override
    public int getPort() {
        return Objects.requireNonNull(ProxyServer.getInstance().getConfig().getListeners().stream().findFirst().orElse(null)).getHost().getPort();
    }

    @Override
    public String getMotd() {
        return Objects.requireNonNull(ProxyServer.getInstance().getConfig().getListeners().stream().findFirst().orElse(null)).getMotd();
    }

}
