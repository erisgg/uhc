package gg.eris.uhc.core.game.player.stat;

import gg.eris.commons.core.identifier.Identifier;

public abstract class AbstractUhcPlayerStat<T> implements UhcPlayerStat<T> {

  private final Identifier identifier;

  public AbstractUhcPlayerStat(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

}
