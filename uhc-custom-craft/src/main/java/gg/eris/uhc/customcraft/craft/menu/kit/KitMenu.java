package gg.eris.uhc.customcraft.craft.menu.kit;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.kit.KitRegistry;
import gg.eris.uhc.customcraft.craft.menu.BackMenuItem;
import gg.eris.uhc.customcraft.craft.menu.main.MainMenu;

public final class KitMenu extends Menu {

  public KitMenu(UhcPlugin plugin, MainMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("kits"), 4);
    setFillItem(Menu.DARK_FILLER);
    setParent(parent);
    addItem(10, new KitMenuItem(KitRegistry.get().getExcavator()));
    addItem(12, new KitMenuItem(KitRegistry.get().getFisher()));
    addItem(14, new KitMenuItem(KitRegistry.get().getForester()));
    addItem(16, new KitMenuItem(KitRegistry.get().getWarrior()));
    addItem(31, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Kits";
  }

}
