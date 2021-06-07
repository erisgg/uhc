package gg.eris.uhc.core.game;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import lombok.Value;

@Value
public class UhcGameSettings {

  String worldName;
  int borderRadius;
  int shrunkBorderRadius;

  int gracePeriodLength;
  int pvpLength;

  int deathmatchBorderShrinkDelay;
  int deathmatchBorderShrinkTime;
  int deathmatchBorderRadius;
  int deathmatchBorderShrunkRadius;
  int deathmatchPlayerThreshold;
  int deathmatchPlayerStartTime;

  int coinsPerKill;
  int coinsPerWin;
  Int2IntMap coinsPerSurvive;

  public UhcGameSettings(String worldName, int borderRadius, int shrunkBorderRadius,
      int gracePeriodLength, int pvpLength, int deathmatchBorderShrinkDelay,
      int deathmatchBorderShrinkTime, int deathmatchBorderRadius, int deathmatchBorderShrunkRadius,
      int deathmatchPlayerThreshold, int deathmatchPlayerStartTime, int coinsPerKill,
      int coinsPerWin, Int2IntMap coinsPerSurvive) {
    this.worldName = worldName;
    this.borderRadius = borderRadius;
    this.shrunkBorderRadius = shrunkBorderRadius;
    this.gracePeriodLength = gracePeriodLength;
    this.pvpLength = pvpLength;
    this.deathmatchBorderShrinkDelay = deathmatchBorderShrinkDelay;
    this.deathmatchBorderShrinkTime = deathmatchBorderShrinkTime;
    this.deathmatchBorderRadius = deathmatchBorderRadius;
    this.deathmatchBorderShrunkRadius = deathmatchBorderShrunkRadius;
    this.deathmatchPlayerThreshold = deathmatchPlayerThreshold;
    this.deathmatchPlayerStartTime = deathmatchPlayerStartTime;
    this.coinsPerKill = coinsPerKill;
    this.coinsPerWin = coinsPerWin;
    this.coinsPerSurvive = Int2IntMaps.unmodifiable(coinsPerSurvive);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String worldName;
    private int borderRadius;
    private int shrunkBorderRadius;

    private int gracePeriodLength;
    private int pvpPeriodLength;

    private int deathmatchBorderShrinkDelay;
    private int deathmatchBorderShrinkTime;
    private int deathmatchBorderRadius;
    private int deathmatchBorderShrunkRadius;
    private int deathmatchPlayerThreshold;
    private int deathmatchPlayerStartTime;

    private int coinsPerKill;
    private int coinsPerWin;
    private Int2IntMap coinsPerSurvive;

    public Builder() {
      this.coinsPerSurvive = new Int2IntArrayMap();
    }

    public UhcGameSettings.Builder worldName(String worldName) {
      this.worldName = worldName;
      return this;
    }

    public UhcGameSettings.Builder borderRadius(int borderRadius) {
      this.borderRadius = borderRadius;
      return this;
    }

    public UhcGameSettings.Builder shrunkBorderRadius(int shrunkBorderRadius) {
      this.shrunkBorderRadius = shrunkBorderRadius;
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

    public UhcGameSettings.Builder deathmatchBorderShrinkDelay(int deathmatchBorderShrinkDelay) {
      this.deathmatchBorderShrinkDelay = deathmatchBorderShrinkDelay;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderShrinkTime(int deathmatchBorderShrinkTime) {
      this.deathmatchBorderShrinkTime = deathmatchBorderShrinkTime;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderRadius(int deathmatchBorderRadius) {
      this.deathmatchBorderRadius = deathmatchBorderRadius;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderShrunkRadius(int deathmatchBorderShrunkRadius) {
      this.deathmatchBorderShrunkRadius = deathmatchBorderShrunkRadius;
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

    public UhcGameSettings.Builder coinsPerSurvive(int topN, int coins) {
      this.coinsPerSurvive.put(topN, coins);
      return this;
    }

    public UhcGameSettings build() {
      return new UhcGameSettings(
          this.worldName,
          this.borderRadius,
          this.shrunkBorderRadius,
          this.gracePeriodLength,
          this.pvpPeriodLength,
          this.deathmatchBorderShrinkDelay,
          this.deathmatchBorderShrinkTime,
          this.deathmatchBorderRadius,
          this.deathmatchBorderShrunkRadius,
          this.deathmatchPlayerThreshold,
          this.deathmatchPlayerStartTime,
          this.coinsPerKill,
          this.coinsPerWin,
          this.coinsPerSurvive
      );
    }
  }

}
