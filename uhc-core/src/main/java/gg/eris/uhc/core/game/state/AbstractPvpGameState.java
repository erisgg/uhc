package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;

public abstract class AbstractPvpGameState<S extends UhcPlayer, T extends UhcGame<S>> extends
    AbstractGameState<S, T> {

  public AbstractPvpGameState(T game) {
    super(game);
  }

  @Override
  public final Type getType() {
    return TypeRegistry.PVP;
  }

}
