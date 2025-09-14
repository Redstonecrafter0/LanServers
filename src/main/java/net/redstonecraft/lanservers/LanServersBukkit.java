package net.redstonecraft.lanservers;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.SocketException;
import java.net.UnknownHostException;

public class LanServersBukkit extends JavaPlugin implements ILanServersPlugin {

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
        return getServer().getPort();
    }

    @Override
    public String getMotd() {
        return getServer().getMotd();
    }

    @Override
    public void logError(String message) {
        getLogger().severe("[LanServers] " + message);
    }

    @Override
    public void logError(String message, Throwable throwable) {
        getLogger().log(java.util.logging.Level.SEVERE, "[LanServers] " + message, throwable);
    }

}
