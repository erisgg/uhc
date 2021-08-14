package gg.eris.uhc.customcraft.craft.menu.recipe.craft;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@RequiredArgsConstructor
public final class RecipeCraftMenuItem implements MenuItem {

  private final int index;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    Craftable craftable = ((RecipeBookMenuViewer) menuViewer).getCraftable();
    if (craftable == null || craftable.getRecipe() == null) {
      return null;
    }

    Recipe recipe = craftable.getRecipe();

    if (recipe instanceof ShapedRecipe) {
      ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;

      int row = Math.floorDiv(this.index, 3);
      int column = index % 3;

      if (shapedRecipe.getShape().length < row) {
        return null;
      }

      String rowString = shapedRecipe.getShape()[row];
      if (rowString.length() < column) {
        return null;
      }

      return shapedRecipe.getIngredientMap().get(rowString.charAt(column));
    } else if (recipe instanceof ShapelessRecipe) {
      ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
      if (this.index >= shapelessRecipe.getIngredientList().size()) {
        return null;
      } else {
        return shapelessRecipe.getIngredientList().get(this.index);
      }
    } else {
      return null;
    }

  }

  @Override
  public void onClick(MenuViewer menuViewer, InventoryClickEvent event) {

  }
}
