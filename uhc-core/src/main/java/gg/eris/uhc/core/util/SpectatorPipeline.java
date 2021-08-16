package gg.eris.uhc.core.util;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.uhc.core.UhcIdentifiers;
import gg.eris.uhc.core.UhcPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class SpectatorPipeline {

  public static void hidePlayer(Player spectator) {
    ErisPlayer erisPlayer =
        UhcPlugin.getPlugin().getCommons().getErisPlayerManager().getPlayer(spectator);
    boolean hasPermission =
        erisPlayer.hasPermission(UhcIdentifiers.VIEWSPECTATORS_PERMISSION);
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (spectator == player) {
        continue;
      }

      // If the online player is not alive
      if (UhcPlugin.getPlugin().getUhc().getGame().isPlayer(player)) {
        if (!UhcPlugin.getPlugin().getCommons().getErisPlayerManager().getPlayer(player)
            .hasPermission(UhcIdentifiers.VIEWSPECTATORS_PERMISSION)) { // If they can't see specs
          player.hidePlayer(spectator); // Hide the current spectator from joining spectator
        }
      } else {
        // If spectator can't see othe spectators (other is a spectator)
        if (!hasPermission) {
          spectator.hidePlayer(player);
        }
      }
    }
  }

}
