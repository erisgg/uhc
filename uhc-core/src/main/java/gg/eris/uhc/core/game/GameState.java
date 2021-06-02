package gg.eris.uhc.core.game;

public enum GameState {

  WAITING,
  COUNTDOWN,
  STARTING,
  GRACE_PERIOD,
  PVP,
  DEATHMATCH,
  ENDED;

  public boolean isInGame() {
    return this == GRACE_PERIOD || this == PVP || this == DEATHMATCH;
  }

  public boolean isNotInGame() {
    return !isInGame();
  }

  public boolean canJoin() {
    return this == WAITING || this == COUNTDOWN;
  }

  public boolean canStart() {
    return this == WAITING || this == COUNTDOWN;
  }

  public boolean canEnd() {
    return this == GRACE_PERIOD || this == PVP || this == DEATHMATCH;
  }

}
