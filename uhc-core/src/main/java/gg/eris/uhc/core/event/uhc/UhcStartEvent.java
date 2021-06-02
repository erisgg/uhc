package gg.eris.uhc.core.event.uhc;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public final class UhcStartEvent<S extends UhcPlayer, T extends UhcGame<S>> extends UhcEvent<S, T> {

  public UhcStartEvent(T game) {
    super(game);
  }

}
