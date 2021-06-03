package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class UhcEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  protected final UhcGame<?> game;

  public UhcEvent(UhcGame<?> game) {
    this.game = game;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

}
