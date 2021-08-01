package gg.eris.uhc.customcraft.craft.perk.impl;

import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.customcraft.craft.Vocation;
import gg.eris.uhc.customcraft.craft.perk.Perk;
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
      int amount = (level - 1) + 2;
      event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, amount));
    }
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }

}
