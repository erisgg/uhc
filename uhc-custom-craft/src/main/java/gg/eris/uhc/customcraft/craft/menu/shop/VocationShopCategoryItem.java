package gg.eris.uhc.customcraft.craft.menu.shop;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class VocationShopCategoryItem implements MenuItem {

  private final Vocation vocation;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    return vocation.getIcon();
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    ((VocationShopMenu) viewer.getViewing()).getMenu(this.vocation).openMenu(viewer);
  }
}
