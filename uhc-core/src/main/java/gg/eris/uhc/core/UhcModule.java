package gg.eris.uhc.core;

import gg.eris.uhc.core.game.UhcGame;

public abstract class UhcModule<T extends UhcGame<?>> {

  protected final UhcPlugin plugin;
  protected final T game;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
    this.game = createGame();
  }

  protected final void enable() {
    onEnable();
    this.game.setup();
    this.game.start();
  }

  protected final void disable() {
    onDisable();
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  protected abstract T createGame();

}
