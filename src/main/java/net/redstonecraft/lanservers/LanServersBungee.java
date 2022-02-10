package net.redstonecraft.lanservers;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.plugin.Plugin;

public class LanServersBungee extends Plugin implements LanServersPlugin {

    @Override
    public int getPort() {
        return ProxyServer.getInstance().getConfig().getListeners().toArray(new ListenerInfo[0])[0].getHost().getPort();
    }

    @Override
    public String getMotd() {
        return ProxyServer.getInstance().getConfig().getListeners().toArray(new ListenerInfo[0])[0].getMotd();
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

}
