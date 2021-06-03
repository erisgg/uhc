package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractDeathmatchGameState;

public final class UhcDeathmatchGameStateEvent extends UhcGameStateChangeEvent {

  public UhcDeathmatchGameStateEvent(UhcGame<?> game,
      AbstractDeathmatchGameState<?, ?> newState) {
    super(game, newState);
  }

}
