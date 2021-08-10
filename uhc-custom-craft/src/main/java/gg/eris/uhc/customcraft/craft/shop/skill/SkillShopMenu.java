package gg.eris.uhc.customcraft.craft.shop.skill;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.shop.skill.vocation.VocationMenu;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import java.util.Map;

public final class SkillShopMenu extends Menu {

  private static final int[] slots = {
      10, 12, 14, 16,
      28, 30, 32, 34
  };

  private final Map<Vocation, Menu> menus;

  public SkillShopMenu(UhcPlugin plugin) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("shop"), 5);
    this.menus = Maps.newHashMap();

    setFillItem(Menu.DARK_FILLER);

    for (int i = 0; i < Vocation.values().length; i++) {
      Vocation vocation = Vocation.values()[i];
      int slot = slots[i];
      addItem(slot, new SkillShopCategoryItem(vocation));
      this.menus.put(vocation, new VocationMenu(plugin, this, vocation));
    }
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "Shop";
  }

  public Menu getMenu(Vocation vocation) {
    return this.menus.get(vocation);
  }

}
