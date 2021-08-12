package gg.eris.uhc.customcraft.craft.menu.main;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.kit.KitMenu;
import gg.eris.uhc.customcraft.craft.menu.shop.VocationShopMenu;
import gg.eris.uhc.customcraft.craft.menu.ultimate.UltimateShopMenu;
import lombok.Getter;

public final class MainMenu extends Menu {

  @Getter
  private final VocationShopMenu vocationShopMenu;

  @Getter
  private final KitMenu kitMenu;

  @Getter
  private final UltimateShopMenu ultimateShopMenu;

  public MainMenu(UhcPlugin plugin) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("main"), 3);
    setFillItem(Menu.DARK_FILLER);
    addItem(11, new KitMenuOpenItem(this.kitMenu = new KitMenu(plugin, this)));
    addItem(13, new VocationMenuOpenItem(this.vocationShopMenu = new VocationShopMenu(plugin, this)));
    addItem(15, new UltimateMenuOpenItem(this.ultimateShopMenu = new UltimateShopMenu(plugin, this)));
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return "UHC Shop";
  }

}
