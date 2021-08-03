package gg.eris.uhc.customcraft.craft.menu;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.recipe.ShapedCraftingRecipe;
import javax.print.DocFlavor.READER;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

@RequiredArgsConstructor
public class CraftingMenuLogic {

  private final UhcPlugin plugin;
  private final CraftingMenu menu;

  protected void handle(InventoryClickEvent event) {
    if (event == null || event.getClickedInventory() == null) {
      return;
    }

    InventoryHolder holder = event.getClickedInventory().getHolder();
    if (!(holder instanceof CraftingMenuInventoryHolder)) {
      return;
    }

    int slot = event.getRawSlot();

    if (!CraftingMenu.USED_SLOTS.contains(slot)) {
      event.setCancelled(true);
      return;
    }

    if (slot == CraftingMenu.OUTPUT_SLOT) {
      handleOutput(event);
    } else {
      Bukkit.getScheduler().runTask(this.plugin, () -> checkInventory(event.getInventory()));
    }
  }

  private void handleOutput(InventoryClickEvent event) {

  }

  private void checkInventory(Inventory inventory) {
    ItemStack[][] matrix = new ItemStack[3][];

    int index = 0;;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (y == 0) {
          matrix[x] = new ItemStack[3];
        }

        matrix[x][y] = inventory.getItem(CraftingMenu.CRAFTING_SLOTS[index++]);
      }
    }

    for (ShapedCraftingRecipe recipe : this.menu.getRecipes()) {
      if (recipe.applicable(matrix)) {
        inventory.setItem(CraftingMenu.OUTPUT_SLOT, recipe.getResultFunction().apply(null));
        return;
      }
    }

    inventory.setItem(CraftingMenu.OUTPUT_SLOT, null);
  }

}
