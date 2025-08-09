# LanServers

This is a small plugin that runs on all major Minecraft Servers.  
The only feature is listing the server as a LanServer in the Multiplayer-Menu of Minecraft.  

# Supported Servers

- Bukkit/Spigot/Paper 1.8+
- Bungeecord 1.16+
- Velocity 3.0.1+

# What does it do exactly?

The same as the Minecraft Client when you open your world to LAN.  

Every 1.5 seconds, the plugin sends a special message with the server port and MOTD to `224.0.2.60` — or `ff75:230::60` for IPv6 — on port 4445 from every interface.
`224.0.2.60` and `ff75:230::60` are _multicast_ addresses which means that everyone in you local network that wants
to get messages sent these addresses will get them, for example, like your Minecraft Client when you're in the Multiplayer Menu.

That's all it does so compatibility should not break when updating to newer Server versions.
