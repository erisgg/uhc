package gg.eris.uhc.customcraft.craft.menu.ultimate;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class UltimateShopItem implements MenuItem {

  private final UltimateCraft ultimate;

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    return this.ultimate.getItem();
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {

  }
}
