package gg.eris.uhc.customcraft.craft.menu.recipe;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.recipe.craft.RecipeCraftMenu;
import lombok.Getter;

public final class RecipeBookMenu extends Menu {

  @Getter
  private final RecipeCraftMenu craftMenu;

  public RecipeBookMenu(UhcPlugin plugin) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("recipe"), 6);
    this.craftMenu = new RecipeCraftMenu(plugin, this);

    setFillItem(Menu.DARK_FILLER);
    int slotIndex = 10;
    int ordinalIndex = 0;
    while (slotIndex < 44) {
      if (slotIndex == 17 || slotIndex == 26 || slotIndex == 35 || slotIndex == 44) {
        slotIndex += 2;
        continue;
      }
      addItem(slotIndex++, new RecipeBookMenuItem(this, ordinalIndex++));
    }

    addItem(48, new RecipeBookMenuPageItem(false));
    addItem(50, new RecipeBookMenuPageItem(true));
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Recipe Book";
  }

}
