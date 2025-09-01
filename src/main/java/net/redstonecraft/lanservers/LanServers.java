package net.redstonecraft.lanservers;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class LanServers {

    private final Timer timer = new Timer();
    private final Inet4Address multicastAddress = (Inet4Address) InetAddress.getByName("224.0.2.60");
    private final static int multicastPort = 4445;
    private final List<MulticastSocket> sources = LanServers.getSourceSockets();
    private final ILanServersPlugin plugin;

    public LanServers(ILanServersPlugin plugin) throws IOException {
        this.plugin = plugin;
        for (MulticastSocket socket : sources) {
            socket.joinGroup(multicastAddress);
        }
    }

    public void enable() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] data = ("[MOTD]" + plugin.getMotd() + "[/MOTD][AD]" + plugin.getPort() + "[/AD]").getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, multicastAddress, multicastPort);
                for (MulticastSocket socket :  sources) {
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1500);
    }

    public void disable() {
        timer.cancel();
        for (MulticastSocket socket :  sources) {
            socket.close();
        }
    }

    private static List<MulticastSocket> getSourceSockets() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .filter(iface -> {
                        try {
                            return iface.supportsMulticast() && iface.isUp() && !iface.isLoopback();
                        } catch (SocketException e) {
                            return false;
                        }
                    })
                    // interface can have multiple addresses, make a loooong list
                    .flatMap(iface -> iface.getInterfaceAddresses().stream()
                            .map(ifaddr -> ifaddr.getAddress())
                            .filter(inaddr -> (inaddr instanceof Inet4Address))
                            .map(inaddr -> new InetSocketAddress(inaddr, multicastPort))
                            .map(insaddr -> {
                                try {
                                    return new MulticastSocket(insaddr);
                                } catch (IOException e) {
                                    return null;
                                }
                            }))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (SocketException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
