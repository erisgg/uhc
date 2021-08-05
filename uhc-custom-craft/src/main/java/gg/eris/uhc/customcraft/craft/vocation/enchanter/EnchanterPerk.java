package gg.eris.uhc.customcraft.craft.vocation.enchanter;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
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
  public String getName() {
    return "Enchanter Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Gives {0}% extra experience per level", level * 3);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }

}
