package gg.eris.uhc.core.game.player.stat.type;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.stat.AbstractUhcPlayerStat;

public final class KillsPlayerStat extends AbstractUhcPlayerStat<Integer> {

  public KillsPlayerStat(Identifier identifier) {
    super(identifier);
  }

  @Override
  public String getDisplayName() {
    return "kills";
  }

  @Override
  public Class<Integer> getDataClass() {
    return Integer.class;
  }

}
