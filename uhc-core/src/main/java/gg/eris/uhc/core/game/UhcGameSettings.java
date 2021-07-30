package gg.eris.uhc.core.game;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class UhcGameSettings {

  // General
  String worldName;
  String netherName;
  int borderRadius;
  int maxHealth;

  // Countdown state
  int requiredPlayers;
  int pregameCountdownDuration;

  // Grace period
  int gracePeriodDuration;

  // PvP period
  int pvpPeriodDuration; // Duration before countdown starts
  int shrunkBorderRadius;
  int deathmatchCountdownDuration;
  int attackCreditDuration;

  // Deathmatch state
  int deathmatchPlayerThreshold;
  int deathmatchBorderShrinkDelay;
  int deathmatchBorderShrinkDuration;
  int deathmatchBorderRadius;
  int deathmatchBorderShrunkRadius;
  int deathmatchBlockDecayDelay;

  int postGameDelay;

  // Coins
  int coinsPerKill;
  int coinsPerWin;
  Int2IntMap coinsPerSurvive;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    // General
    private String worldName;
    private String netherName;
    private int borderRadius;
    private int maxHealth;

    // Countdown state
    int requiredPlayers;
    int pregameCountdownDuration;

    // Grace period
    private int gracePeriodDuration;

    // PvP period
    private int pvpPeriodDuration;
    private int shrunkBorderRadius;
    private int deathmatchCountdownDuration;
    private int attackCreditDuration;

    // Deathmatch state
    private int deathmatchPlayerThreshold;
    private int deathmatchBorderShrinkDelay;
    private int deathmatchBorderShrinkDuration;
    private int deathmatchBorderRadius;
    private int deathmatchBorderShrunkRadius;
    private int deathmatchBlockDecayDelay;

    private int postGameDelay;

    // Coins
    private int coinsPerKill;
    private int coinsPerWin;
    private final Int2IntMap coinsPerSurvive;

    public Builder() {
      this.coinsPerSurvive = new Int2IntArrayMap();
    }

    public UhcGameSettings.Builder worldName(String worldName) {
      this.worldName = worldName;
      return this;
    }

    public UhcGameSettings.Builder netherName(String netherName) {
      this.netherName = netherName;
      return this;
    }

    public UhcGameSettings.Builder pregameCountdownDuration(int pregameCountdownDuration) {
      this.pregameCountdownDuration = pregameCountdownDuration;
      return this;
    }

    public UhcGameSettings.Builder maxHealth(int maxHealth) {
      this.maxHealth = maxHealth;
      return this;
    }

    public UhcGameSettings.Builder requiredPlayers(int requiredPlayers) {
      this.requiredPlayers = requiredPlayers;
      return this;
    }

    public UhcGameSettings.Builder gracePeriodDuration(int gracePeriodDuration) {
      this.gracePeriodDuration = gracePeriodDuration;
      return this;
    }

    public UhcGameSettings.Builder pvpPeriodDuration(int pvpPeriodDuration) {
      this.pvpPeriodDuration = pvpPeriodDuration;
      return this;
    }

    public UhcGameSettings.Builder deathmatchPlayerThreshold(int deathmatchPlayerThreshold) {
      this.deathmatchPlayerThreshold = deathmatchPlayerThreshold;
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

    public UhcGameSettings.Builder deathmatchCountdownDuration(int deathmatchCountdownDuration) {
      this.deathmatchCountdownDuration = deathmatchCountdownDuration;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderShrinkDelay(int deathmatchBorderShrinkDelay) {
      this.deathmatchBorderShrinkDelay = deathmatchBorderShrinkDelay;
      return this;
    }

    public UhcGameSettings.Builder deathmatchBorderShrinkDuration(
        int deathmatchBorderShrinkDuration) {
      this.deathmatchBorderShrinkDuration = deathmatchBorderShrinkDuration;
      return this;
    }

    public UhcGameSettings.Builder attackCreditDuration(int attackCreditDuration) {
      this.attackCreditDuration = attackCreditDuration;
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

    public UhcGameSettings.Builder deathmatchBlockDecayDelay(int deathmatchBlockDecayDelay) {
      this.deathmatchBlockDecayDelay = deathmatchCountdownDuration;
      return this;
    }

    public UhcGameSettings.Builder postGameDelay(int postGameDelay) {
      this.postGameDelay = postGameDelay;
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
          this.netherName,
          this.borderRadius,
          this.maxHealth,
          this.requiredPlayers,
          this.pregameCountdownDuration,
          this.gracePeriodDuration,
          this.pvpPeriodDuration,
          this.shrunkBorderRadius,
          this.deathmatchCountdownDuration,
          this.attackCreditDuration,
          this.deathmatchPlayerThreshold,
          this.deathmatchBorderShrinkDelay,
          this.deathmatchBorderShrinkDuration,
          this.deathmatchBorderRadius,
          this.deathmatchBorderShrunkRadius,
          this.deathmatchBlockDecayDelay,
          this.postGameDelay,
          this.coinsPerKill,
          this.coinsPerWin,
          Int2IntMaps.unmodifiable(this.coinsPerSurvive)
      );
    }
  }

}
