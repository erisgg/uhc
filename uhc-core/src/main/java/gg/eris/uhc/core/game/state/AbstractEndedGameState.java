package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractEndedGameState<S extends UhcPlayer, T extends UhcGame<S>> extends
    AbstractGameState<S, T> {

  public AbstractEndedGameState(T game) {
    super(game);
  }

  @Override
  public final Type getType() {
    return TypeRegistry.ENDED;
  }

  @Override
  public boolean canStart() {
    GameState<?, ?> state = this.game.getGameState();
    return state.getType() != TypeRegistry.WAITING
        && state.getType() != TypeRegistry.COUNTDOWN
        && state.getType() != TypeRegistry.STARTING;
  }

}
