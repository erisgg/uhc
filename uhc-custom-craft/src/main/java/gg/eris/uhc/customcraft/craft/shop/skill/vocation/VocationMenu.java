package gg.eris.uhc.customcraft.craft.shop.skill.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.shop.BackMenuItem;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;

public final class VocationMenu extends Menu {

  private static final int BACK_SLOT = 49;

  private final Vocation vocation;

  public VocationMenu(UhcPlugin plugin, Menu parent, Vocation vocation) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("vocation-menu-" + vocation.name()), 6);
    this.vocation = vocation;

    setFillItem(Menu.DARK_FILLER);
    setParent(parent);

    if (vocation.getRegistry() == null) {
      return;
    }

    // Hardcoded joy :D :D :D :D
    addItem(9, new VocationMenuItem(vocation.getRegistry().getFirstCraft(), 100, 9));
    addItem(27, new VocationMenuItem(vocation.getRegistry().getSecondCraft(), 100, 27));
    addItem(10, new VocationMenuItem(vocation.getRegistry().getPerk(), 250, 10, 9));
    addItem(11, new VocationMenuItem(vocation.getRegistry().getPerk(), 500, 11, 10));
    addItem(12, new VocationMenuItem(vocation.getRegistry().getPerk(), 1_000, 12, 11));
    addItem(28, new VocationMenuItem(vocation.getRegistry().getPerk(), 250, 28, 27));
    addItem(29, new VocationMenuItem(vocation.getRegistry().getPerk(), 500, 29, 28));
    addItem(30, new VocationMenuItem(vocation.getRegistry().getPerk(), 1_000, 30, 29));
    addItem(21, new VocationMenuItem(vocation.getRegistry().getFirstTrinket(), 5_000, 21, 12, 30));
    addItem(22, new VocationMenuItem(vocation.getRegistry().getThirdCraft(), 7_500, 22, 12, 30));
    addItem(14, new VocationMenuItem(vocation.getRegistry().getPerk(), 10_000, 14, 22));
    addItem(15, new VocationMenuItem(vocation.getRegistry().getPerk(), 15_000, 15, 14));
    addItem(32, new VocationMenuItem(vocation.getRegistry().getPerk(), 10_000, 32, 22));
    addItem(33, new VocationMenuItem(vocation.getRegistry().getPerk(), 15_000, 33, 32));
    addItem(25, new VocationMenuItem(vocation.getRegistry().getFourthCraft(), 25_000, 25, 15, 33));
    addItem(26, new VocationMenuItem(vocation.getRegistry().getSecondTrinket(), 25_000, 26, 25));

    addItem(BACK_SLOT, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return this.vocation.getDisplay() + " Shop";
  }
}
