package net.redstonecraft.lanservers;

public interface ILanServersPlugin {

    int getPort();
    String getMotd();

    void logError(String message);
    void logError(String message, Throwable throwable);

}
