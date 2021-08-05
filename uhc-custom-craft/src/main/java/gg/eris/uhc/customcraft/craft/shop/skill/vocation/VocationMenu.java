package gg.eris.uhc.customcraft.craft.shop.skill.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.shop.BackMenuItem;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;

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
    // Hardcoded joy :)
    addItem(9, new VocationMenuItem(vocation.getRegistry().getFirstCraft()));
    addItem(27, new VocationMenuItem(vocation.getRegistry().getSecondCraft()));
    addItem(10, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(11, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(12, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(28, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(29, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(30, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(21, new VocationMenuItem(vocation.getRegistry().getFirstTrinket()));
    addItem(22, new VocationMenuItem(vocation.getRegistry().getThirdCraft()));
    addItem(14, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(15, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(32, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(33, new VocationMenuItem(vocation.getRegistry().getPerk()));
    addItem(25, new VocationMenuItem(vocation.getRegistry().getFourthCraft()));
    addItem(26, new VocationMenuItem(vocation.getRegistry().getSecondTrinket()));

    addItem(BACK_SLOT, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return this.vocation.getDisplay() + " Shop";
  }
}
