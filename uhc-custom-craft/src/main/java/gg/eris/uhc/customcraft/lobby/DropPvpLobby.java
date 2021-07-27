package gg.eris.uhc.customcraft.lobby;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.core.lobby.region.type.HeightActivatedPvpLobbyRegion;
import gg.eris.uhc.core.lobby.region.type.StaticLobbyRegion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class DropPvpLobby extends Lobby {

  public DropPvpLobby(UhcPlugin plugin, Location spawn, int yLevel) {
    super(plugin, spawn);

    addRegion(1, new StaticLobbyRegion(plugin, this) {
      @Override
      public boolean isInRegion(Location location) {
        return location.getY() > yLevel;
      }

      @Override
      public void onEnter(Player player) {
        PlayerUtil.resetPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
      }

      @Override
      public void onLeave(Player player) {

      }
    });

    addRegion(2, new HeightActivatedPvpLobbyRegion(plugin, this));
  }


}
