package net.redstonecraft.lanservers

import com.google.inject.Inject
import org.spongepowered.api.Game
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.event.game.state.GameStoppedServerEvent
import org.spongepowered.api.plugin.Plugin
import org.spongepowered.api.text.serializer.TextSerializers

@Plugin(
    id = "lanservers",
    name = "LanServers",
    version = "1.0.0",
    description = "Lists the Server on the Multiplayer list as LanServer if on the same network.",
    url = "https://github.com/Redstonecrafter0/LanServers",
    authors = ["Redstonecrafter0"]
)
class LanServersSponge: LanServersPlugin {

    override val port: Int
        get() = game.server.boundAddress.get().port
    override val motd: String
        get() = TextSerializers.LEGACY_FORMATTING_CODE.serialize(game.server.motd)

    @Inject
    private lateinit var game: Game

    @Listener
    fun onStart(event: GameStartedServerEvent) = enable()
    @Listener
    fun onStop(event: GameStoppedServerEvent) = disable()
}
