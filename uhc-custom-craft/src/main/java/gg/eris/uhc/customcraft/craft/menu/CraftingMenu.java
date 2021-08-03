package gg.eris.uhc.customcraft.craft.menu;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.craft.TestCraft;
import gg.eris.uhc.customcraft.craft.recipe.ShapedCraftingRecipe;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Was easier to not extend menu as this is too complex
 */
public final class CraftingMenu {

  public static final String TITLE = "Crafting Table";

  protected static final IntSet USED_SLOTS = IntSets.unmodifiable(new IntArraySet(Set.of(
      11, 12, 13, 20, 21, 22, 29, 30, 31, 24
  )));

  protected static final int[] CRAFTING_SLOTS = {
    11, 12, 13,
    20, 21, 22,
    29, 30, 31
  };

  protected static final int OUTPUT_SLOT = 24;

  private final CraftingMenuLogic logic;

  @Getter
  private final Set<ShapedCraftingRecipe> recipes;

  public CraftingMenu(UhcPlugin plugin) {
    this.logic = new CraftingMenuLogic(plugin, this);
    this.recipes = Set.of(new TestCraft());
  }

  public void openMenu(Player player) {
    CraftingMenuInventoryHolder holder = new CraftingMenuInventoryHolder();
    Inventory inventory = Bukkit.createInventory(holder, 54, TITLE);
    holder.setInventory(inventory);

    for (int i = 0; i < 54; i++) {
      inventory.setItem(i, Menu.DARK_FILLER);
    }

    inventory.setItem(OUTPUT_SLOT, null);
    for (int slot : CRAFTING_SLOTS) {
      inventory.setItem(slot, null);
    }

    player.openInventory(inventory);
  }

  public void handle(InventoryClickEvent event) {
    this.logic.handle(event);
  }

}
