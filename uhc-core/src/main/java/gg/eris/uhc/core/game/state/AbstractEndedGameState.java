package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.Type;

public abstract class AbstractEndedGameState<S extends UhcPlayer, T extends UhcGame<S>> extends AbstractGameState<S, T> {

  public AbstractEndedGameState(T game) {
    super(game);
  }

  @Override
  public Type getType() {
    return Type.ENDED;
  }

  @Override
  public boolean canStart() {
    GameState<?, ?> state = this.game.getGameState();
    return state.getType() != Type.WAITING
        && state.getType() != Type.COUNTDOWN
        && state.getType() != Type.STARTING;
  }

}
