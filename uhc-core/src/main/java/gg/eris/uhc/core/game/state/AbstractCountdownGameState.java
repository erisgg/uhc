package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractCountdownGameState<S extends UhcPlayer, T extends UhcGame<S>> extends
    AbstractGameState<S, T> {

  public AbstractCountdownGameState(T game) {
    super(game);
  }

  @Override
  public final GameState.Type getType() {
    return TypeRegistry.COUNTDOWN;
  }

}
