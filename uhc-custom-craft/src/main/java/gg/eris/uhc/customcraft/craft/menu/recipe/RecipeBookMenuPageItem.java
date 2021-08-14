package gg.eris.uhc.customcraft.craft.menu.recipe;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class RecipeBookMenuPageItem implements MenuItem {

  private static final ItemStack FORWARD = new ItemBuilder(Material.ARROW)
      .withName(CC.GREEN + "Next Page").build();

  private static final ItemStack BACK = new ItemBuilder(Material.ARROW)
      .withName(CC.GREEN + "Previous Page").build();

  private final boolean forward;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    return this.forward ? FORWARD : BACK;
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    RecipeBookMenuViewer recipeBookMenuViewer = (RecipeBookMenuViewer) viewer;
    if (this.forward) {
      recipeBookMenuViewer.setPage(recipeBookMenuViewer.getPage() + 1);
      viewer.getViewing().updateMenu(viewer);
    } else {
      if (recipeBookMenuViewer.getPage() > 0) {
        recipeBookMenuViewer.setPage(recipeBookMenuViewer.getPage() + 1);
        viewer.getViewing().updateMenu(viewer);
      }
    }
  }
}
