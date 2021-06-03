package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public interface GameState<S extends UhcPlayer, T extends UhcGame<S>> {

  enum Type {
    WAITING,
    COUNTDOWN,
    STARTING,
    GRACE_PERIOD,
    PVP,
    DEATHMATCH,
    ENDED;
  }

  Type getType();

  void start();
  void end();
  boolean canStart();
  void tick();

}
