package gg.eris.uhc.core;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.stat.UhcPlayerStatRegistry;
import gg.eris.uhc.core.lobby.Lobby;

public abstract class UhcModule<T extends UhcGame<?>> {

  protected final UhcPlugin plugin;
  protected final T game;
  protected final Lobby lobby;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
    this.game = createGame();
    this.lobby = createLobby();
  }

  protected final void enable() {
    this.game.setupWorld();
    enableLobby();
    onEnable();
  }

  protected final void disable() {
    onDisable();
  }

  public final void enableLobby() {
    this.lobby.enable();
  }

  public final void disableLobby() {
    this.lobby.disable();
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  protected abstract T createGame();

  protected abstract UhcPlayerStatRegistry createStatRegistry();

  protected abstract Lobby createLobby();

}
