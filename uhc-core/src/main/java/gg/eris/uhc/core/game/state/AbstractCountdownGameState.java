package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.Type;

public abstract class AbstractCountdownGameState<S extends UhcPlayer, T extends UhcGame<S>> extends AbstractGameState<S, T> {

  public AbstractCountdownGameState(T game) {
    super(game);
  }

  @Override
  public GameState.Type getType() {
    return Type.COUNTDOWN;
  }

  @Override
  public boolean canStart() {
    return this.game.getGameState().getType() == Type.WAITING;
  }

}
