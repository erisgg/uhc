package gg.eris.uhc.customcraft.craft.menu.ultimate;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.BackMenuItem;
import gg.eris.uhc.customcraft.craft.menu.main.MainMenu;

public class UltimateShopMenu extends Menu {

  public UltimateShopMenu(UhcPlugin plugin, MainMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("ultimate"), 6);
    setParent(parent);
    setFillItem(Menu.DARK_FILLER);
    addItem(49, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Ultimate Shop";
  }
}
