package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class TrinketBagListener extends MultiStateListener {

  private final CustomCraftUhcGame game;

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.GRACE_PERIOD,
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH
    );
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }


  @EventHandler
  public void onDropItem(PlayerDropItemEvent event) {
    if (TrinketBag.isBag(event.getItemDrop().getItemStack())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    if (TrinketBag.isBag(event.getCurrentItem())) {
      if (player.getOpenInventory().getTopInventory().getType() != InventoryType.CRAFTING) {
        event.setCancelled(true);
        player.closeInventory();
        TextController.send(
            player,
            TextType.INFORMATION,
            "You <h>cannot</h> move your trinket whilst an inventory is open."
        );
      }
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    Player player = (Player) event.getPlayer();
    ItemStack cursor = player.getItemOnCursor();
    if (TrinketBag.isBag(cursor)) {
      player.setItemOnCursor(null);
      player.getInventory().addItem(cursor);
    }

    if (event.getInventory().getType() == InventoryType.CRAFTING) {
      return;
    }

    for (ItemStack item : event.getInventory().getContents()) {
      if (TrinketBag.isBag(item)) {
        event.getInventory().remove(item);
        event.getPlayer().getInventory().addItem(item);
      }
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (TrinketBag.isBag(event.getItem())) {
      event.setCancelled(true);

      CustomCraftUhcPlayer player = this.game.getPlayer(event.getPlayer());
      if (player == null) {
        return;
      }
      if (event.getAction() == Action.RIGHT_CLICK_AIR
          || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        TrinketBag.openBag(player);
      }
    }
  }


}
