package gg.eris.uhc.customcraft.craft.menu.main;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class KitShopItem implements MenuItem {

  private static final ItemStack ITEM = new ItemBuilder(Material.WOOD_PICKAXE)
      .withName(CC.GOLD.bold() + "Kit Menu")
      .withLore(CC.GOLD.italic() + "Click to enter the kits menu")
      .build();

  private final Menu menu;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    return ITEM;
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    this.menu.openMenu(viewer);
  }

}
