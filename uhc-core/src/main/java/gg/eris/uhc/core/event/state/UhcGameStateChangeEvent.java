package gg.eris.uhc.core.event.state;

import gg.eris.uhc.core.event.UhcEvent;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.GameState;
import lombok.Getter;

public abstract class UhcGameStateChangeEvent extends UhcEvent {

  @Getter
  private final GameState<?, ?> newState;

  public UhcGameStateChangeEvent(UhcGame<?> game, GameState<?, ?> newState) {
    super(game);
    this.newState = newState;
  }

  public GameState<?, ?> getOldState() {
    return this.game.getGameState();
  }

}
