package gg.eris.uhc.core.lobby.type;

import gg.eris.erisspigot.event.entity.PlayerMoveBlockEvent;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import java.util.function.Function;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class PvpLobby extends Lobby {

  private final Function<Location, Boolean> regionChecker;

  public PvpLobby(UhcPlugin plugin, Function<Location, Boolean> regionChecker) {
    super(plugin);
    this.regionChecker = regionChecker;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockMove(PlayerMoveBlockEvent event) {
    if (regionChecker.apply(event.getTo()) /*&& not in pvp*/) {
      // Setup PvP for them
    }
  }

}
