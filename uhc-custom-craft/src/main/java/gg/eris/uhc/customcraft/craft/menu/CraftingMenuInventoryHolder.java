package gg.eris.uhc.customcraft.craft.menu;

import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class CraftingMenuInventoryHolder implements InventoryHolder {

  private Inventory inventory;

  @Override
  public Inventory getInventory() {
    return this.inventory;
  }

  public void setInventory(Inventory inventory) {
    Validate.isTrue(this.inventory == null, "cannot reset inventory");
    this.inventory = inventory;
  }
}
