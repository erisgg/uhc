package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;

public final class UhcStartingGameStateEvent extends UhcGameStateChangeEvent {

  public UhcStartingGameStateEvent(UhcGame<?> game, AbstractStartingGameState<?, ?> newState) {
    super(game, newState);
  }

}
