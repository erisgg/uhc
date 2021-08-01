package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import lombok.Getter;
import org.bukkit.event.Event;

public abstract class UhcEvent extends Event {


  @Getter
  protected final UhcGame<?> game;

  public UhcEvent(UhcGame<?> game) {
    this.game = game;
  }


}
