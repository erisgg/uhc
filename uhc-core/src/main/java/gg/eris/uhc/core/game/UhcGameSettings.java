package gg.eris.uhc.core.game;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UhcGameSettings {

  String worldName;
  int borderSize;

  int gracePeriodLength;
  int pvpLength;
  int deathmatchBorderShrinkDelay;
  int deathmatchBorderShrinkInterval;
  int deathmatchPlayerThreshold;
  int deathmatchPlayerStartTime;

  int coinsPerKill;
  int coinsPerWin;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String worldName;
    private int borderSize;

    private int gracePeriodLength;
    private int pvpPeriodLength;
    private int deathmatchBorderShrinkDelay;
    private int deathmatchBorderShrinkInterval;
    private int deathmatchPlayerThreshold;
    private int deathmatchPlayerStartTime;

    private int coinsPerKill;
    private int coinsPerWin;

    public UhcGameSettings build() {
      return new UhcGameSettings(
          this.worldName,
          this.borderSize,
          this.gracePeriodLength,
          this.pvpPeriodLength,
          this.deathmatchBorderShrinkDelay,
          this.deathmatchBorderShrinkInterval,
          this.deathmatchPlayerThreshold,
          this.deathmatchPlayerStartTime,
          this.coinsPerKill,
          this.coinsPerWin
      );
    }
  }

}
