package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;

public final class UhcGracePeriodGameStateEvent extends UhcGameStateChangeEvent {

  public UhcGracePeriodGameStateEvent(UhcGame<?> game,
      AbstractGracePeriodGameState<?, ?> newState) {
    super(game, newState);
  }

}
