package gg.eris.uhc.core.game.player.stat.type;

import gg.eris.uhc.core.game.player.stat.UhcPlayerStat;

public final class WinsPlayerStat implements UhcPlayerStat<Integer> {

  @Override
  public String getName() {
    return "wins";
  }

  @Override
  public Class<Integer> getDataClass() {
    return Integer.class;
  }
}
