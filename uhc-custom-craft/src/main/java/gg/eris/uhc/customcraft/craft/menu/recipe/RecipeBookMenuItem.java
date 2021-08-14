package gg.eris.uhc.customcraft.craft.menu.recipe;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class RecipeBookMenuItem implements MenuItem {

  private final RecipeBookMenu menu;
  private final int ordinalIndex;
  
  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    Craftable craftable = getCraftable(viewer);
    if (craftable == null || craftable.getRecipe() == null) {
      return null;
    } else {
      if (craftable instanceof Craft) {
        return ((Craft) craftable).getDisplayItem();
      }
      return craftable.getRecipe().getResult();
    }
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    Craftable craftable = getCraftable(viewer);
    if (craftable != null) {
      ((RecipeBookMenuViewer) viewer).setCraftable(craftable);
      this.menu.getCraftMenu().openMenu(viewer);
    }
  }

  private Craftable getCraftable(MenuViewer viewer) {
    int page = ((RecipeBookMenuViewer) viewer).getPage();
    return Vocation.getCraftable(this.ordinalIndex + page * 27);
  }

}
