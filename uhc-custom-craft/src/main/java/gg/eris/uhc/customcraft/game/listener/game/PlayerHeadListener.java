package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.healer.HealerVocationRegistry;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public class PlayerHeadListener extends GameStateListener {

  private final CustomCraftUhcGame uhcGame;

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player handle = event.getPlayer();
    ItemStack item = handle.getItemInHand();

    if (item == null || item.getType() != Material.SKULL_ITEM
        || item.getData().getData() != (byte) SkullType.PLAYER.ordinal()) {
      return;
    } else if (HealerVocationRegistry.get().getFirstCraft().isItem(item)) {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      event.getPlayer()
          .addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4 * 20, 2), true);

      int healerLevel = ((CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame()
          .getPlayer(handle))
          .getPerkLevel(Vocation.HEALER.getRegistry().getPerk());

      if (healerLevel > 0) {
        int duration = (5 + (healerLevel - 1)) * 20;
        event.getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1), true);
      }

      event.setCancelled(true);

      if (!StackUtil.decrement(item)) {
        event.getPlayer().setItemInHand(null);
      }

      event.getPlayer().updateInventory();
    }
  }


}
