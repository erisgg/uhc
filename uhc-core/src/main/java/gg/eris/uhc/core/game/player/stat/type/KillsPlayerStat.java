package gg.eris.uhc.core.game.player.stat.type;

import gg.eris.uhc.core.game.player.stat.UhcPlayerStat;

public class KillsPlayerStat implements UhcPlayerStat<Integer> {

  @Override
  public String getName() {
    return "kills";
  }

  @Override
  public Class<Integer> getDataClass() {
    return Integer.class;
  }
}
