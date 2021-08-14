package gg.eris.uhc.core.game;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UhcGameSettings {

  // General
  String worldName;
  String netherName;
  String deathmatchName;
  int borderRadius;
  int maxHealth;

  // Countdown state
  int requiredPlayers;
  int pregameCountdownDuration;

  // Grace period
  int gracePeriodDuration;

  // PvP period
  int pvpPeriodDuration; // Duration before countdown starts
  int borderShrunkRadius;
  int borderShrinkDelay;
  int borderShrinkDuration;
  int preDeathmatchCountdownDuration;
  int attackCreditDuration;

  // Deathmatch state
  int deathmatchPlayerThreshold;
  int deathmatchBorderShrinkDelay;
  int deathmatchBorderShrinkDuration;
  int deathmatchBorderRadius;
  int deathmatchBorderShrunkRadius;
  int deathmatchBlockDecayDelay;
  int deathmatchStartCountdownDuration;

  int postGameShutdownDelay;

  // Coins
  int coinsPerKill;
  int coinsPerWin;
  Int2IntMap coinsPerSurvive;

}
