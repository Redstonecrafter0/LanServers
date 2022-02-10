package net.redstonecraft.lanservers;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public interface LanServersPlugin {

    int getPort();
    String getMotd();

    default void enable() {
        try {
            Internal.socket = new DatagramSocket();
        } catch (SocketException ignored) {}
        Internal.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] data = ("[MOTD]" +  getMotd() + "[/MOTD][AD]" + getPort() + "[/AD]").getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, Internal.multicastAddress, Internal.multicastPort);
                try {
                    Internal.socket.send(packet);
                } catch (IOException ignored) {}
            }
        }, 0, 1500);
    }

    default void disable() {
        Internal.timer.cancel();
        Internal.socket.close();
    }

    class Internal {
        static Timer timer = new Timer();
        static InetAddress multicastAddress;
        static int multicastPort = 4445;
        static DatagramSocket socket;

        static {
            try {
                multicastAddress = InetAddress.getByName("224.0.2.60");
            } catch (UnknownHostException ignored) {}
        }
    }

}
