package gg.eris.uhc.core.game.player;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.rank.Rank;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * The {@link UhcPlayer} is a wrapper around the {@link Player} class. It will be extended by any
 * UHC implementation and all player-data can be stored in this class.
 */
public abstract class UhcPlayer extends ErisPlayer {

  public UhcPlayer(DefaultData data) {
    super(data);
  }


}
