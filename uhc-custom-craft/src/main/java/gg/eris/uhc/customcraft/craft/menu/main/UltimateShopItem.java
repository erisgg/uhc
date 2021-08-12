package gg.eris.uhc.customcraft.craft.menu.main;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.menu.ultimate.UltimateShopMenu;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class UltimateShopItem implements MenuItem {

  private static final ItemStack ITEM = new ItemBuilder(Material.NETHER_STAR)
      .withName(CC.YELLOW.bold() + "Ultimate Menu")
      .withLore(CC.YELLOW.italic() + "Click to enter the ultimate shop")
      .build();

  private final UltimateShopMenu menu;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    return ITEM;
  }

  @Override
  public void onClick(MenuViewer menuViewer, InventoryClickEvent event) {
    this.menu.openMenu(menuViewer);
  }
}
