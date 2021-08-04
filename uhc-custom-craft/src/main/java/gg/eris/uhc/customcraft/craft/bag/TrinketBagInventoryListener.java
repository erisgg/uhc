package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    Player player = (Player) event.getWhoClicked();
    ItemStack cursor = event.getCursor();
    TrinketBagItem item = ((TrinketBagInventoryHolder) inventory.getHolder()).getBag();

    int index = TrinketBagItem.INVENTORY_SLOTS.indexOf(slot);
    Trinket clicked = item.getTrinket(index);
    if (clicked == null) {
      if (StackUtil.isNullOrAir( cursor)) {
        return;
      }
      return;
    }

    if (player.getInventory().firstEmpty() == -1) {
      TextController.send(
          player,
          TextType.ERROR,
          "Your inventory is <h>full</h>!"
      );
      return;
    }

    Trinket trinket = item.removeTrinket(index);
    player.getInventory().addItem(trinket.getItem());

    if (StackUtil.isNullOrAir(cursor)) {
      inventory.setItem(slot, TrinketBagItem.EMPTY_SLOT);
    } else {
      // TODO: Get NBT from thing
      Unlockable newUnlockable  = Vocation.getUnlockable(null);
      if (!(newUnlockable instanceof Trinket)) {
        return;
      }
      Trinket newTrinket = (Trinket) newUnlockable;
      item.addTrinket(newTrinket, index);
      inventory.setItem(slot, cursor);
      player.setItemOnCursor(null);
    }
  }


  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    if (isInventory(event.getInventory())) {
      event.setCancelled(true);
    }
  }

  private boolean isInventory(Inventory inventory) {
    return inventory != null && inventory.getHolder() instanceof TrinketBagInventoryHolder;
  }
}
