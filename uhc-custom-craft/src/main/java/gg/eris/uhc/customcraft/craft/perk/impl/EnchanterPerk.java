package gg.eris.uhc.customcraft.craft.perk.impl;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.uhc.customcraft.craft.Vocation;
import gg.eris.uhc.customcraft.craft.perk.Perk;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

/**
 * Gives 3% exp extra per level
 */
public final class EnchanterPerk extends Perk {

  private final ErisPlayerManager erisPlayerManager;

  public EnchanterPerk(ErisPlayerManager erisPlayerManager) {
    super("enchanter_perk");
    this.erisPlayerManager = erisPlayerManager;
  }

  @EventHandler
  public void onExpGain(PlayerExpChangeEvent event) {
    CustomCraftUhcPlayer player = erisPlayerManager.getPlayer(event.getPlayer());
    int level = getLevel(player);
    event.setAmount((int) Math.ceil(event.getAmount() + event.getAmount() * 0.03 * level));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }

}
