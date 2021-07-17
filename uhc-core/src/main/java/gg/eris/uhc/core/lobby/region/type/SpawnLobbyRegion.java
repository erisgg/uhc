package gg.eris.uhc.core.lobby.region.type;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public final class SpawnLobbyRegion extends LobbyRegion {

  public SpawnLobbyRegion(UhcPlugin plugin, Lobby lobby) {
    super(plugin, lobby);

    registerPlayerEvent(PlayerJoinEvent.class, event -> Bukkit.getScheduler()
        .runTask(plugin, () -> event.getPlayer().teleport(lobby.getSpawn())));

    registerPlayerEvent(PlayerRespawnEvent.class, event -> Bukkit.getScheduler()
        .runTask(plugin, () -> event.getPlayer().teleport(lobby.getSpawn())));
  }

  @Override
  public void onEnter(Player player) {
  }

  @Override
  public void onLeave(Player player) {
  }

  @Override
  public boolean isInRegion(Location location) {
    return true;
  }

}
