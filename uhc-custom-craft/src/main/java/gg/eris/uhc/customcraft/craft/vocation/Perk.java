package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.Listener;

public abstract class Perk extends VocationUnlockable implements Listener {

  public Perk(String identifierValue) {
    super(identifierValue);
  }

  public abstract String getDescription(int level);

  public final int getLevel(CustomCraftUhcPlayer player) {
    return player.getPerkLevel(getIdentifier());
  }

}
