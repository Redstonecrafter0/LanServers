package net.redstonecraft.lanservers;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class LanServersBukkit extends JavaPlugin implements ILanServersPlugin {

    private LanServers lanServers = null;

    @Override
    public void onEnable() {
        if (lanServers == null) {
            try {
                lanServers = new LanServers(this);
            } catch (IOException e) {
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

}
