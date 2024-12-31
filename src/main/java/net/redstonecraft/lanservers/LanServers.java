package net.redstonecraft.lanservers;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class LanServers {

    private final Timer timer = new Timer();
    private final InetAddress multicastAddress = InetAddress.getByName("224.0.2.60");
    private final int multicastPort = 4445;
    private final DatagramSocket socket = new DatagramSocket();
    private final ILanServersPlugin plugin;

    public LanServers(ILanServersPlugin plugin) throws UnknownHostException, SocketException {
        this.plugin = plugin;
    }

    public void enable() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] data = ("[MOTD]" + plugin.getMotd() + "[/MOTD][AD]" + plugin.getPort() + "[/AD]").getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, multicastAddress, multicastPort);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1500);
    }

    public void disable() {
        timer.cancel();
        socket.close();
    }

}
