package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractGracePeriodGameState<S extends UhcPlayer, T extends UhcGame<S>> extends AbstractGameState<S, T> {

  public AbstractGracePeriodGameState(T game) {
    super(game);
  }

  @Override
  public Type getType() {
    return Type.GRACE_PERIOD;
  }

  @Override
  public boolean canStart() {
    return this.game.getGameState().getType() == Type.STARTING;
  }

}
