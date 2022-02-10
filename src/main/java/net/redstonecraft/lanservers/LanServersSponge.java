package net.redstonecraft.lanservers;

import com.google.inject.Inject;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.serializer.TextSerializers;

@Plugin(
        id = "lanservers",
        name = "LanServers",
        version = "1.0.0",
        description = "Lists the Server on the Multiplayer list as LanServer if on the same network.",
        url = "https://github.com/Redstonecrafter0/LanServers",
        authors = {"Redstonecrafter0"}
)
public class LanServersSponge implements LanServersPlugin {

    @Inject
    private Game game;

    @Override
    public int getPort() {
        return game.getServer().getBoundAddress().get().getPort();
    }

    @Override
    public String getMotd() {
        return TextSerializers.LEGACY_FORMATTING_CODE.serialize(game.getServer().getMotd());
    }

    @Listener
    public void onStart(GameStartedServerEvent event) {
        enable();
    }

    @Listener
    public void onStop(GameStoppedServerEvent event) {
        disable();
    }

}
