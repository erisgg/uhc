package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
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
    if (!TrinketBag.INVENTORY_SLOTS_SET.contains(slot)) {
      return;
    }

    Player handle = (Player) event.getWhoClicked();
    TrinketBag trinketBag = ((TrinketBagInventoryHolder) inventory.getHolder()).getBag();
    CustomCraftUhcPlayer player = trinketBag.getPlayer();
    ItemStack cursor = event.getCursor();

    int index = TrinketBag.INVENTORY_SLOTS.indexOf(slot);

    Trinket clicked = trinketBag.getTrinket(index);
    if (clicked != null) {
      if (handle.getInventory().firstEmpty() == -1) {
        TextController.send(
            handle,
            TextType.ERROR,
            "Your inventory is <h>full</h>."
        );
        return;
      }

      if (clicked.canRemove(player)) {
        Trinket trinket = trinketBag.removeTrinket(index);
        trinket.onRemove(player);
        handle.getInventory().addItem(trinket.getItem());
      } else {
        TextController.send(
            handle,
            TextType.ERROR,
            "You <h>cannot</h> remove that trinket."
        );
        return;
      }
    }

    if (StackUtil.isNullOrAir(cursor)) {
      inventory.setItem(slot, TrinketBag.EMPTY_SLOT);
    } else {
      Identifier identifier = VocationUnlockable.getIdentifierFromItemStack(cursor);
      VocationUnlockable newUnlockable = Vocation.getUnlockable(identifier);
      if (!(newUnlockable instanceof Trinket)) {
        return;
      }
      Trinket newTrinket = (Trinket) newUnlockable;

      if (trinketBag.hasTrinket(newTrinket)) {
        TextController.send(
            handle,
            TextType.ERROR,
            "You already have that trinket <h>equipped</h>."
        );
      } else {
        trinketBag.addTrinket(newTrinket, index);
        newTrinket.onAdd(player);
        inventory.setItem(slot, cursor);
        handle.setItemOnCursor(null);
      }

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
