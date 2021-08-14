package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.healer.HealerVocationRegistry;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class PlayerHeadListener extends GameStateListener {

  private   final CustomCraftUhcGame uhcGame;

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
    } else if (HealerVocationRegistry.get().getFirstCraft().isItem(item)) {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      event.getPlayer()
          .addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4 * 20, 2), true);

      event.setCancelled(true);

      if (!StackUtil.decrement(item)) {
        event.getPlayer().setItemInHand(null);
      }

      event.getPlayer().updateInventory();
    }
  }


}
