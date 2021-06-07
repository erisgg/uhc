package gg.eris.uhc.core.game.player.stat.type;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.stat.AbstractUhcPlayerStat;

public final class GamesPlayedPlayerStat extends AbstractUhcPlayerStat<Integer> {

  public GamesPlayedPlayerStat(Identifier identifier) {
    super(identifier);
  }

  @Override
  public String getDisplayName() {
    return "games played";
  }

  @Override
  public Class<Integer> getDataClass() {
    return Integer.class;
  }

}
