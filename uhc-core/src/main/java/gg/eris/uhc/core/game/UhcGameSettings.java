package gg.eris.uhc.core.game;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UhcGameSettings {

  String worldName;
  int borderRadius;

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
    private int borderRadius;

    private int gracePeriodLength;
    private int pvpPeriodLength;
    private int deathmatchBorderShrinkDelay;
    private int deathmatchBorderShrinkInterval;
    private int deathmatchPlayerThreshold;
    private int deathmatchPlayerStartTime;

    private int coinsPerKill;
    private int coinsPerWin;

    public UhcGameSettings.Builder worldName(String worldName) {
      this.worldName = worldName;
      return this;
    }

    public UhcGameSettings.Builder borderRadius(int borderRadius) {
      this.borderRadius = borderRadius;
      return this;
    }

    public UhcGameSettings.Builder gracePeriodLength(int gracePeriodLength) {
      this.gracePeriodLength = gracePeriodLength;
      return this;
    }

    public UhcGameSettings.Builder pvpPeriodLength(int pvpPeriodLength) {
      this.pvpPeriodLength = pvpPeriodLength;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderSrhinkDelay(int deathmatchBorderShrinkDelay) {
      this.deathmatchBorderShrinkDelay = deathmatchBorderShrinkDelay;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderShrinkInterval(int deathmatchBorderShrinkInterval) {
      this.deathmatchBorderShrinkInterval = deathmatchBorderShrinkInterval;
      return this;
    }

    public UhcGameSettings.Builder deathmatchPlayerThreshold(int deathmatchPlayerThreshold) {
      this.deathmatchPlayerThreshold = deathmatchPlayerThreshold;
      return this;
    }

    public UhcGameSettings.Builder deathmatchPlayerStartTime(int deathmatchPlayerStartTime) {
      this.deathmatchPlayerStartTime = deathmatchPlayerStartTime;
      return this;
    }

    public UhcGameSettings.Builder coinsPerKill(int coinsPerKill) {
      this.coinsPerKill = coinsPerKill;
      return this;
    }

    public UhcGameSettings.Builder coinsPerWin(int coinsPerWin) {
      this.coinsPerWin = coinsPerWin;
      return this;
    }

    public UhcGameSettings build() {
      return new UhcGameSettings(
          this.worldName,
          this.borderRadius,
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
