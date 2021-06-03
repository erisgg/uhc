package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractGameState<S extends UhcPlayer, T extends UhcGame<S>> implements GameState<S, T> {

  protected final T game;
  protected int ticks;

  public AbstractGameState(T game) {
    this.game = game;
    this.ticks = 0;
  }

  @Override
  public final void tick() {
    this.ticks++;
    this.onTick();
  }

  public abstract void onTick();

}
