package gg.eris.uhc.customcraft.craft.menu.ultimate;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.BackMenuItem;
import gg.eris.uhc.customcraft.craft.menu.main.MainMenu;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateRegistry;

public final class UltimateShopMenu extends Menu {

  public UltimateShopMenu(UhcPlugin plugin, MainMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("ultimate"), 6);
    setParent(parent);
    setFillItem(Menu.DARK_FILLER);

    int index = 10;
    for (UltimateCraft ultimate : UltimateRegistry.get().getOrdered()) {
      if (index == 17 || index == 26 || index == 35 || index == 44) {
        index += 2;
        continue;
      }

      addItem(index++, new UltimateShopItem(ultimate));
    }

    while (index < 44) {
      if (index == 17 || index == 26 || index == 35 || index == 44) {
        index += 2;
        continue;
      }
      addItem(index++, new UltimateShopItem(null));
    }

    addItem(49, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Ultimate Shop";
  }
}
