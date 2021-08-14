package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    ItemStack item = event.getPlayer().getItemInHand();

    if (item == null || item.getType() != Material.SKULL_ITEM) {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      // Better than doing reflection to check for the matching skin blob.
      boolean gold =
          item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName()
              .equals("Golden Head");

      int regenLevel = gold ? 3 : 2;
      int regenTime = gold ? (5 * 20) : (4 * 20);

      int absorptionLevel = gold ? 1 : 0;
      int absorptionTime = 90 * 20;

      PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, regenTime, regenLevel);
      PotionEffect absorption = new PotionEffect(PotionEffectType.ABSORPTION, absorptionTime,
          absorptionLevel);

      event.getPlayer().addPotionEffect(regen, true);
      event.getPlayer().addPotionEffect(absorption, true);

      event.setCancelled(true);

      if (!StackUtil.decrement(item)) {
        event.getPlayer().setItemInHand(null);
      }

      event.getPlayer().updateInventory();
    }
  }
}
