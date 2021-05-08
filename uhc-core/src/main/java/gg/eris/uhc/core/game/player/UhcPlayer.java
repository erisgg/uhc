package gg.eris.uhc.core.game.player;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class UhcPlayer {

  private final UUID playerUuid;

  public UhcPlayer(Player player) {
    this.playerUuid = player.getUniqueId();
  }

  public final boolean isOnline() {
    Player player = getPlayer();
    return player != null && player.isOnline();
  }

  public final Player getPlayer() {
    return Bukkit.getPlayer(this.playerUuid);
  }

  public final UUID getUniqueId() {
    return this.playerUuid;
  }

}
