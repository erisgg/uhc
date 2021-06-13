package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import lombok.Getter;
import org.bukkit.event.Cancellable;

@Getter
public final class UhcTickEvent extends UhcEvent implements Cancellable {

  private final int tick;

  private boolean cancelled;

  public UhcTickEvent(UhcGame<?> game, int tick) {
    super(game);
    this.tick = tick;
    this.cancelled = false;
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
}
