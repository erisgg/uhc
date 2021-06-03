package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.Type;

public abstract class AbstractPvpGameState<S extends UhcPlayer, T extends UhcGame<S>> extends AbstractGameState<S, T> {

  public AbstractPvpGameState(T game) {
    super(game);
  }

  @Override
  public Type getType() {
    return Type.PVP;
  }

  @Override
  public boolean canStart() {
    return this.game.getGameState().getType() == Type.GRACE_PERIOD;
  }

}
