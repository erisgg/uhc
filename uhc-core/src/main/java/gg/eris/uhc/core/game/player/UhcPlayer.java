package gg.eris.uhc.core.game.player;

import gg.eris.commons.bukkit.player.ErisPlayer;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * The {@link UhcPlayer} is a wrapper around the {@link Player} class. It will be extended by any
 * UHC implementation and all player-data can be stored in this class.
 */
public abstract class UhcPlayer extends ErisPlayer {

  public UhcPlayer(UUID uuid, String name, Set<String> knownAliases, long firstLogin,
      long lastLogin, long lastLogout, UUID uuid1) {
    super(uuid, name, knownAliases, firstLogin, lastLogin, lastLogout);
  }


}
