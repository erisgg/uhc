package gg.eris.uhc.core.game.player;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.util.Pair;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

/**
 * The {@link UhcPlayer} is a wrapper around the {@link Player} class. It will be extended by any
 * UHC implementation and all player-data can be stored in this class.
 */
public abstract class UhcPlayer extends ErisPlayer {

  @Getter
  private Pair<UUID, Long> lastAttacker;

  @Getter
  @Setter
  private boolean alive;

  @Getter
  private int gamesPlayed;

  @Getter
  private int wins;

  @Getter
  private int kills;

  @Getter
  private int gameKills;


  public UhcPlayer(DefaultData data, int wins, int kills, int gamesPlayed) {
    super(data);

    this.alive = false;
    this.wins = wins;
    this.gamesPlayed = gamesPlayed;
    this.kills = kills;
    this.gameKills = 0;
  }

  public void incrementKills() {
    this.kills++;
    this.gameKills++;
  }

  public void playedGame() {
    this.gamesPlayed++;
  }

  public void won() {
    this.wins++;
  }

  public void setLastAttacker(Player player) {
    lastAttacker = Pair.of(player.getUniqueId(), System.currentTimeMillis());
  }

}
