package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import java.awt.MenuItem;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public final class TrinketBagInventoryListener extends MultiStateListener {

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
  public void onBagClick(InventoryClickEvent event) {
    if (!isInventory(event.getClickedInventory())) {
      return;
    }

    Inventory inventory = event.getClickedInventory();

    event.setCancelled(true);

    int slot = event.getRawSlot();
    if (!TrinketBagItem.INVENTORY_SLOTS_SET.contains(slot)) {
      return;
    }

    TrinketBagItem item = ((TrinketBagInventoryHolder) inventory.getHolder()).getBag();

    int index = TrinketBagItem.INVENTORY_SLOTS.indexOf(slot);
    Trinket clicked = item.getTrinket(index);
    if (clicked == null) {
      return;
    }

    item.removeTrinket(index);
    inventory.setItem(slot, Menu.LIGHT_FILLER);
  }


  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    if (isInventory(event.getInventory())) {
      event.setCancelled(true);
    }
  }

  private boolean isInventory(Inventory inventory) {
    return inventory.getHolder() instanceof TrinketBagInventoryHolder;
  }
}
