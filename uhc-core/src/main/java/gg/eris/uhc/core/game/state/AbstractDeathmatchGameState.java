package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractDeathmatchGameState<S extends UhcPlayer, T extends UhcGame<S>> extends
    AbstractGameState<S, T> {

  public AbstractDeathmatchGameState(T game) {
    super(game);
  }

  @Override
  public final GameState.Type getType() {
    return Type.DEATHMATCH;
  }

  @Override
  public boolean canStart() {
    return this.game.getGameState().getType() == Type.GRACE_PERIOD
        || this.game.getGameState().getType() == Type.PVP;
  }

}
