package gg.eris.uhc.core.game.player;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * The {@link UhcPlayer} is a wrapper around the {@link Player} class. It will be extended by any
 * UHC implementation and all player-data can be stored in this class.
 */
public abstract class UhcPlayer {

  private final UUID uuid;

  public UhcPlayer(Player player) {
    this(player.getUniqueId());
  }

  public UhcPlayer(UUID uuid) {
    this.uuid = uuid;
  }

  public final boolean isOnline() {
    Player player = getHandle();
    return player != null && player.isOnline();
  }

  public final Player getHandle() {
    return Bukkit.getPlayer(this.uuid);
  }

  public final UUID getUniqueId() {
    return this.uuid;
  }

}
