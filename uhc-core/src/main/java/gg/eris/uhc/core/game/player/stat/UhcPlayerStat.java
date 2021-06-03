package gg.eris.uhc.core.game.player.stat;

public interface UhcPlayerStat<T> {

  String getName();

  Class<T> getDataClass();

}
