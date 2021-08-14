package gg.eris.uhc.customcraft.craft.menu.recipe.craft;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

@RequiredArgsConstructor
public final class RecipeCraftMenuOutputItem implements MenuItem {


  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    Craftable craftable = ((RecipeBookMenuViewer) menuViewer).getCraftable();
    if (craftable == null || craftable.getRecipe() == null) {
      return null;
    }

    return craftable.getRecipe().getResult();
  }

  @Override
  public void onClick(MenuViewer menuViewer, InventoryClickEvent event) {

  }
}
