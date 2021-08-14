package gg.eris.uhc.customcraft.craft.menu.recipe.craft;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.BackMenuItem;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenu;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;

public final class RecipeCraftMenu extends Menu {

  public RecipeCraftMenu(UhcPlugin plugin, RecipeBookMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("craft"), 6);
    setFillItem(Menu.DARK_FILLER);
    setParent(parent);

    addItem(10, new RecipeCraftMenuItem(0));
    addItem(11, new RecipeCraftMenuItem(1));
    addItem(12, new RecipeCraftMenuItem(2));
    addItem(19, new RecipeCraftMenuItem(3));
    addItem(20, new RecipeCraftMenuItem(4));
    addItem(21, new RecipeCraftMenuItem(5));
    addItem(28, new RecipeCraftMenuItem(6));
    addItem(29, new RecipeCraftMenuItem(7));
    addItem(30, new RecipeCraftMenuItem(8));
    addItem(23, new RecipeCraftMenuOutputItem());

    addItem(49, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return ((VocationUnlockable) ((RecipeBookMenuViewer) viewer).getCraftable()).getName()
        + "  Craft ";
  }
}
