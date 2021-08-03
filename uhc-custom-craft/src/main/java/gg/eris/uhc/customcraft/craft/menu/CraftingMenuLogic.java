package gg.eris.uhc.customcraft.craft.menu;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.recipe.ShapedCraftingRecipe;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

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
      handleTakingOutput(event);
    } else {
      Bukkit.getScheduler().runTask(this.plugin, () -> checkInventory(event.getInventory()));
    }
  }

  private void handleTakingOutput(InventoryClickEvent event) {
    Player player = (Player) event.getWhoClicked();
    Inventory inventory = event.getClickedInventory();
    ItemStack clicked = event.getCurrentItem();
    ItemStack cursor = event.getCursor();
    ClickType type = event.getClick();
    ItemStack[][] matrix = getMatrix(inventory);
    ShapedCraftingRecipe recipe = getRecipe(matrix);

    event.setCancelled(true);

    if (recipe == null) {
      return;
    }

    if (type.isShiftClick()) {
      int count = 0;
      while (recipe.applicable(matrix)) {
        recipe.consume(matrix);
        count++;
      }

      while (count-- > 0) {
        player.getInventory().addItem(event.getCurrentItem());
      }

      updateInventory(inventory, matrix);
      checkInventory(inventory);
      player.updateInventory();
      return;
    } else if (type != ClickType.LEFT) {
      return;
    }

    // Getting the current cursor amount
    int cursorAmount = 0;
    if (!StackUtil.isNullOrAir(cursor)) {
      cursorAmount = cursor.getAmount();
      if (!clicked.isSimilar(cursor) || clicked.getAmount()
          + cursor.getAmount() > clicked.getMaxStackSize()) {
        return;
      }
    }

    ItemStack newCursor = clicked.clone();
    newCursor.setAmount(clicked.getAmount() + cursorAmount);

    recipe.consume(matrix);

    updateInventory(inventory, matrix);
    checkInventory(inventory);
    player.setItemOnCursor(newCursor);
  }

  protected void handle(InventoryDragEvent event) {
    if (event == null || event.getInventory() == null) {
      return;
    }

    InventoryHolder holder = event.getInventory().getHolder();
    if (!(holder instanceof CraftingMenuInventoryHolder)) {
      return;
    }

    Set<Integer> slots = event.getRawSlots();

    if (slots.contains(CraftingMenu.OUTPUT_SLOT)) {
      event.setCancelled(true);
      return;
    }

    Bukkit.getScheduler().runTask(this.plugin, () -> checkInventory(event.getInventory()));
  }

  private void checkInventory(Inventory inventory) {
    ItemStack[][] matrix = getMatrix(inventory);
    ShapedCraftingRecipe recipe = getRecipe(matrix);
    if (recipe != null) {
      inventory.setItem(CraftingMenu.OUTPUT_SLOT, recipe.getResultFunction().apply(null));
    } else {
      inventory.setItem(CraftingMenu.OUTPUT_SLOT, Menu.LIGHT_FILLER);
    }
  }

  private ShapedCraftingRecipe getRecipe(ItemStack[][] matrix) {
    for (ShapedCraftingRecipe recipe : this.menu.getRecipes()) {
      if (recipe.applicable(matrix)) {
        return recipe;
      }
    }
    return null;
  }

  private ItemStack[][] getMatrix(Inventory inventory) {
    ItemStack[][] matrix = new ItemStack[3][];
    int index = 0;
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        if (y == 0) {
          matrix[x] = new ItemStack[3];
        }
        matrix[x][y] = inventory.getItem(CraftingMenu.CRAFTING_SLOTS.get(index++));
      }
    }
    return matrix;
  }

  private void updateInventory(Inventory inventory, ItemStack[][] matrix) {
    int index = 0;
    for (ItemStack[] row : matrix) {
      for (ItemStack item : row) {
        inventory.setItem(CraftingMenu.CRAFTING_SLOTS.get(index++), item);
      }
    }
  }

}
