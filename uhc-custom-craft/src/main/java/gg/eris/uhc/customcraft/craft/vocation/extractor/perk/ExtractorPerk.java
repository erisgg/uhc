package gg.eris.uhc.customcraft.craft.vocation.extractor.perk;

import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

/**
 * Gold nugget drops 2 per kill + 1 per level
 */
public final class ExtractorPerk extends Perk {

  public ExtractorPerk() {
    super("extractor_perk");
  }

  @EventHandler
  public void onUhcPlayerDeath(UhcPlayerDeathEvent event) {
    if (event.getKiller() == null) {
      return;
    }

    CustomCraftUhcPlayer killer = (CustomCraftUhcPlayer) event.getKiller();
    Player handle = killer.getHandle();
    if (handle == null) {
      return;
    }

    int level = getLevel(killer);
    if (level > 0) {
      int amount = getAmount(level);
      event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, amount));
    }
  }

  @Override
  public String getName() {
    return "Extractor Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Grants {0} gold nuggets when you kill a player",
        getAmount(level));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }

  private int getAmount(int level) {
    return 1 + level;
  }

}
