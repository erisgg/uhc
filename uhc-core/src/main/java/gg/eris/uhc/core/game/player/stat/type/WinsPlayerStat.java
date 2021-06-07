package gg.eris.uhc.core.game.player.stat.type;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.stat.AbstractUhcPlayerStat;
import gg.eris.uhc.core.game.player.stat.UhcPlayerStat;

public final class WinsPlayerStat extends AbstractUhcPlayerStat<Integer> {

  public WinsPlayerStat(Identifier identifier) {
    super(identifier);
  }

  @Override
  public String getDisplayName() {
    return "wins";
  }

  @Override
  public Class<Integer> getDataClass() {
    return Integer.class;
  }

}
