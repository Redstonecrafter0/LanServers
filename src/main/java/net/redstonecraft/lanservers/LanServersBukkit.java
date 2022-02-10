package net.redstonecraft.lanservers;

import org.bukkit.plugin.java.JavaPlugin;

public class LanServersBukkit extends JavaPlugin implements LanServersPlugin {

    @Override
    public int getPort() {
        return getServer().getPort();
    }

    @Override
    public String getMotd() {
        return getServer().getMotd();
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
