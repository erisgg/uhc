package gg.eris.uhc.core.game.player.stat;

import gg.eris.commons.core.identifier.Identifiable;

public interface UhcPlayerStat<T> extends Identifiable {

  String getDisplayName();

  Class<T> getDataClass();

}
