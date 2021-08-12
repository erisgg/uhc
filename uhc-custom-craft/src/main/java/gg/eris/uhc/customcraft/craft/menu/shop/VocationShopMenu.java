package gg.eris.uhc.customcraft.craft.menu.shop;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.BackMenuItem;
import gg.eris.uhc.customcraft.craft.menu.main.MainMenu;
import gg.eris.uhc.customcraft.craft.menu.shop.vocation.VocationMenu;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import java.util.Map;

public final class VocationShopMenu extends Menu {

  private static final int[] slots = {
      10, 12, 14, 16,
      28, 30, 32, 34
  };

  private final Map<Vocation, Menu> menus;

  public VocationShopMenu(UhcPlugin plugin, MainMenu parent) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("shop"), 6);
    this.menus = Maps.newHashMap();

    setFillItem(Menu.DARK_FILLER);
    setParent(parent);

    for (int i = 0; i < Vocation.values().length; i++) {
      Vocation vocation = Vocation.values()[i];
      int slot = slots[i];
      addItem(slot, new VocationShopCategoryItem(vocation));
      this.menus.put(vocation, new VocationMenu(plugin, this, vocation));
    }

    addItem(49, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Skill Shop";
  }

  public Menu getMenu(Vocation vocation) {
    return this.menus.get(vocation);
  }

}
