package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import lombok.Getter;

@Getter
public final class UhcTickEvent extends UhcEvent {

  private final int tick;

  public UhcTickEvent(UhcGame<?> game, int tick) {
    super(game);
    this.tick = tick;
  }

}
