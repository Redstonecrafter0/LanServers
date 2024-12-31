# LanServers

This is a small plugin written in Kotlin that runs on all major Minecraft Servers.  
The only feature is listing the server as a LanServer in the Multiplayer-Menu of Minecraft.  

# Supported Servers
- Bukkit/Spigot/Paper 1.8+
- Bungeecord 1.16+
- Sponge 7.2+
- Velocity 3.0.1+

# What does it do exactly?
The same as the Minecraft Client when you open your world to LAN.  
It sends every 1.5 seconds a special message with the server port and MOTD to `224.0.2.60` on port 4445.
`224.0.2.60` is a Multicast Address which means that everyone in you local network that wants
to get messages sent to `224.0.2.60` will get them like your Minecraft Client when you're in the Multiplayer Menu.

That's all it does so compatibility should not break when updating to newer Server versions.
