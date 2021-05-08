package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public abstract class UhcGame<T extends UhcPlayer> {

  private final Map<UUID, T> players;

  protected final UhcGameSettings settings;

  public UhcGame(UhcGameSettings settings) {
    this.players = Maps.newHashMap();
    this.settings = settings;
  }

  public void addPlayer(T player) {
    this.players.put(player.getUniqueId(), player);
  }

  public T removePlayer(Player player) {
    return removePlayer(player.getUniqueId());
  }

  public T removePlayer(UUID uuid) {
    return this.players.remove(uuid);
  }

  public final T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  public final T getPlayer(UUID uuid) {
    return this.players.get(uuid);
  }

  public final UhcGameSettings getSettings() {
    return this.settings;
  }

}
