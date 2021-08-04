package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.core.util.Validate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class TrinketBagInventoryHolder implements InventoryHolder {

  @Getter
  private final ItemStack item;
  @Getter
  private final TrinketBagItem bag;
  private Inventory inventory;

  @Override
  public Inventory getInventory() {
    return this.inventory;
  }

  public void setInventory(Inventory inventory) {
    Validate.isNull(this.inventory, "cannot reset inventory");
    this.inventory = inventory;
  }
}
