package net.redstonecraft.lanservers;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LanServers {

    private final Timer timer = new Timer();
    private final InetAddress multicastAddress = InetAddress.getByName("224.0.2.60");
    private final int multicastPort = 4445;
    private List<DatagramSocket> sockets = Collections.emptyList();
    private final ILanServersPlugin plugin;

    public LanServers(ILanServersPlugin plugin) throws UnknownHostException, SocketException {
        this.plugin = plugin;
    }

    public void enable() {
        try {
            rebuildSockets();
        } catch (SocketException e) {
            plugin.logError("Failed to rebuild sockets", e);
            return;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] data = ("[MOTD]" + plugin.getMotd() + "[/MOTD][AD]" + plugin.getPort() + "[/AD]").getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(data, data.length, multicastAddress, multicastPort);
                for (DatagramSocket socket : sockets) {
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        if (System.getProperty("dev.redstones.lanservers.debug") != null) {
                            plugin.logError("Failed sending multicast packet on " + socket.getLocalAddress().getHostAddress(), e);
                        }
                    }
                }
            }
        }, 0, 1500);
    }

    private Stream<InetAddress> socketsStreamFromProperty(String bindAddressesProperty) {
        return Arrays.stream(bindAddressesProperty.split(","))
                .map(i -> {
                    try {
                        InetAddress address = InetAddress.getByName(i);
                        if (address.getHostAddress().equals(i)) {
                            return address;
                        }
                        plugin.logError("Bind address '" + i + "' is not valid");
                    } catch (UnknownHostException e) {
                        plugin.logError("Bind address '" + i + "' is not valid", e);
                    }
                    return null;
                })
                .filter(Objects::nonNull);
    }

    private Stream<InetAddress> socketsStreamFromEnumeration() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .filter(i -> {
                    try {
                        return i.supportsMulticast();
                    } catch (SocketException e) {
                        plugin.logError("Unexpected exception while checking interface for multicast support", e);
                        return false;
                    }
                })
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                .filter(i -> i instanceof Inet4Address);
    }

    private void rebuildSockets() throws SocketException {
        String bindAddressesProperty = System.getProperty("dev.redstones.lanservers.bindAddresses");
        Stream<InetAddress> bindAddressesStream;
        if (bindAddressesProperty != null && !bindAddressesProperty.isEmpty()) {
            bindAddressesStream = socketsStreamFromProperty(bindAddressesProperty);
        } else {
            bindAddressesStream = socketsStreamFromEnumeration();
        }
        sockets = bindAddressesStream.map(i -> {
                    try {
                        return new DatagramSocket(0, i);
                    } catch (SocketException e) {
                        plugin.logError("Failed to create socket for " + i.getHostAddress(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void disable() {
        timer.cancel();
        for (DatagramSocket socket : sockets) {
            socket.close();
        }
        sockets.clear();
    }

}
