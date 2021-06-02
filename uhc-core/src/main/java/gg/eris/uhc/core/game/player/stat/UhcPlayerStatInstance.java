package gg.eris.uhc.core.game.player.stat;

import gg.eris.uhc.core.game.player.UhcPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class UhcPlayerStatInstance<T, S extends UhcPlayerStat<T>> {

  private final UhcPlayer player;
  private final S stat;

  @Setter
  private T value;

  public UhcPlayerStatInstance(UhcPlayer player, S stat, T value) {
    this.player = player;
    this.stat = stat;
    this.value = value;
  }

}
