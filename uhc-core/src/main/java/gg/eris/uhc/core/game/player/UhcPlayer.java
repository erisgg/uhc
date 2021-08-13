package gg.eris.uhc.core.game.player;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.util.Pair;
import gg.eris.uhc.core.UhcPlugin;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * The {@link UhcPlayer} is a wrapper around the {@link Player} class. It will be extended by any
 * UHC implementation and all player-data can be stored in this class.
 */
public abstract class UhcPlayer extends ErisPlayer {

  private Pair<UUID, Long> lastAttacker;

  @Getter
  protected int gamesPlayed;

  @Getter
  protected int wins;

  @Getter
  protected int kills;

  @Getter
  protected int deaths;

  @Getter
  protected int gameKills;


  public UhcPlayer(DefaultData data, int gamesPlayed, int wins, int kills, int deaths) {
    super(data);
    this.gamesPlayed = gamesPlayed;
    this.wins = wins;
    this.kills = kills;
    this.deaths = deaths;
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

  public void died() {
    this.deaths++;
  }

  public void setLastAttacker(Player player) {
    this.lastAttacker = Pair.of(player.getUniqueId(), System.currentTimeMillis());
  }

  public Pair<UUID, Long> getLastAttacker() {
    if (this.lastAttacker != null) {
      if (this.lastAttacker.getValue() + UhcPlugin.getPlugin().getUhc().getGame().getSettings().getAttackCreditDuration() * 1000L < System.currentTimeMillis()) {
        this.lastAttacker = null;
      }
    }

    return this.lastAttacker;
  }
}
