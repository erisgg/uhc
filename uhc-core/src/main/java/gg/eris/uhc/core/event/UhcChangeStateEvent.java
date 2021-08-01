package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.GameState;
import lombok.Getter;
import org.bukkit.event.HandlerList;

public final class UhcChangeStateEvent extends UhcEvent {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  private GameState<?, ?> state;

  public UhcChangeStateEvent(UhcGame<?> game, GameState<?, ?> state) {
    super(game);
    this.state = state;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }


}
