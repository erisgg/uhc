package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractWaitingGameState<S extends UhcPlayer, T extends UhcGame<S>> extends
    AbstractGameState<S, T> {

  public AbstractWaitingGameState(T game) {
    super(game);
  }

  @Override
  public boolean canStart() {
    return this.game.getGameState().getType() == TypeRegistry.COUNTDOWN;
  }

  @Override
  public final Type getType() {
    return TypeRegistry.WAITING;
  }
}
