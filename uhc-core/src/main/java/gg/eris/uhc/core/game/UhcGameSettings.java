package gg.eris.uhc.core.game;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
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

  int postGameDelay;

  // Coins
  int coinsPerKill;
  int coinsPerWin;
  Int2IntMap coinsPerSurvive;

}
