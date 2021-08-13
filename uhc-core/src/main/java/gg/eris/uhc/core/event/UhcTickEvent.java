package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
public final class UhcTickEvent extends UhcEvent implements Cancellable {

  private static final HandlerList HANDLERS = new HandlerList();

  private final long tick;

  private boolean cancelled;

  public UhcTickEvent(UhcGame<?> game, long tick) {
    super(game);
    this.tick = tick;
    this.cancelled = false;
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }
}
