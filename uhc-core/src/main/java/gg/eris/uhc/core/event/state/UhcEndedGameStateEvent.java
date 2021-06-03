package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractEndedGameState;

public final class UhcEndedGameStateEvent extends UhcGameStateChangeEvent {

  public UhcEndedGameStateEvent(UhcGame<?> game,
      AbstractEndedGameState<?, ?> newState) {
    super(game, newState);
  }

}
