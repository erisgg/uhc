package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.customcraft.lobby.DropPvpLobby;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class CustomCraftUhcModule extends UhcModule<CustomCraftUhcGame> {

  public CustomCraftUhcModule(UhcPlugin plugin) {
    super(plugin);
  }

  @Override
  public void onEnable() {

  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected CustomCraftUhcGame createGame() {
    return new CustomCraftUhcGame(this.plugin, this);
  }

  @Override
  protected Lobby createLobby() {
    return new DropPvpLobby(
        this.plugin,
        new Location(
            Bukkit.getWorld("pregame_world"),
            0.5,
            73,
            0.5,
            -90.0f,
            0.0f
        ),
        60
    );
  }
}
