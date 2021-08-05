package gg.eris.uhc.customcraft.craft.shop;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.menu.item.CloseMenuItem;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class BackMenuItem extends CloseMenuItem {

  private static final ItemStack ITEM = new ItemBuilder(Material.ARROW)
      .withName(CC.WHITE + "Back")
      .build();

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    return ITEM;
  }

}
