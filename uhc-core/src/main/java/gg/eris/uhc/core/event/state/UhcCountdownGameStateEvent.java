package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractCountdownGameState;

public final class UhcCountdownGameStateEvent extends UhcGameStateChangeEvent {

  public UhcCountdownGameStateEvent(UhcGame<?> game,
      AbstractCountdownGameState<?, ?> newState) {
    super(game, newState);
  }

}
