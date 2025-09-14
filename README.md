# LanServers

This is a small plugin that runs on all major Minecraft Servers.  
The only feature is listing the server as a LanServer in the Multiplayer-Menu of Minecraft.  

# Supported Servers
- Bukkit/Spigot/Paper 1.8+ (and Folia)
- Bungeecord 1.16+
- Velocity 3.0.1+

# What does it do exactly?
The same as the Minecraft Client when you open your world to LAN.  
It sends every 1.5 seconds a special message with the server port and MOTD to `224.0.2.60` on port 4445.
`224.0.2.60` is a Multicast Address which means that everyone in your local network that wants
to get messages sent to `224.0.2.60` will get them like your Minecraft Client when you're in the Multiplayer Menu.

That's all it does, so compatibility should not break when updating to newer Server versions.

# Config
To keep the plugin small and working on all servers, there is no config file.
Instead, Java System Properties are used, which you can set in your server startup script
using `-Ddev.redstones.lanservers.setting=value` before the `-jar` argument.

| Java System Property                     | Description                                       | Default Behaviour                |
|------------------------------------------|---------------------------------------------------|----------------------------------|
| `dev.redstones.lanservers.bindAddresses` | Comma separated list of IP addresses to send from | All interface addresses are used |
