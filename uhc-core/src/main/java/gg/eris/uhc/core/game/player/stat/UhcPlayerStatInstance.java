package gg.eris.uhc.core.game.player.stat;

import gg.eris.uhc.core.game.player.UhcPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UhcPlayerStatInstance<T, S extends UhcPlayerStat<T>> {

  private final UhcPlayer player;
  private final S statInstance;

  @Setter
  private T value;

  public UhcPlayerStatInstance(UhcPlayer player, S statInstance, T value) {
    this.player = player;
    this.statInstance = statInstance;
    this.value = value;
  }

}
