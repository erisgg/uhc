package gg.eris.uhc.customcraft.craft.menu.recipe.craft;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenu;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;

public final class RecipeCraftMenu extends Menu {

  public RecipeCraftMenu(UhcPlugin plugin, RecipeBookMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("craft"), 5);
    setFillItem(Menu.DARK_FILLER);
    setParent(parent);
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return ((VocationUnlockable) ((RecipeBookMenuViewer) viewer).getCraftable()).getName()
        + "  Craft ";
  }
}
