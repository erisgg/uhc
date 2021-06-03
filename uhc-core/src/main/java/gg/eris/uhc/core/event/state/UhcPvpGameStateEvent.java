package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractPvpGameState;

public final class UhcPvpGameStateEvent extends UhcGameStateChangeEvent {

  public UhcPvpGameStateEvent(UhcGame<?> game,
      AbstractPvpGameState<?, ?> newState) {
    super(game, newState);
  }

}
