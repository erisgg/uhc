package gg.eris.uhc.customcraft.game.player;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class CustomCraftUhcTiers {

  public static int getPoints(int kills, int wins) {
    return kills + wins * 10;
  }

  public static int getTier(int points) {
    if (points < getPointsForTier(1)) {
      return 0;
    } else if (points < getPointsForTier(2)) {
      return 1;
    } else if (points < getPointsForTier(3)) {
      return 2;
    } else if (points < getPointsForTier(4)) {
      return 3;
    } else if (points < getPointsForTier(5)) {
      return 4;
    } else if (points < getPointsForTier(6)) {
      return 5;
    } else if (points < getPointsForTier(7)) {
      return 6;
    } else if (points < getPointsForTier(8)) {
      return 7;
    } else if (points < getPointsForTier(9)) {
      return 8;
    } else if (points < getPointsForTier(10)) {
      return 9;
    } else {
      return 10;
    }
  }

  public static int getPointsForTier(int tier) {
    switch (tier) {
      case 0:
      default:
        return 0;
      case 1:
        return 20;
      case 2:
        return 70;
      case 3:
        return 220;
      case 4:
        return 520;
      case 5:
        return 1_020;
      case 6:
        return 1_770;
      case 7:
        return 2_770;
      case 8:
        return 5_270;
      case 9:
        return 10_270;
      case 10:
        return 20_000;
    }
  }
}
