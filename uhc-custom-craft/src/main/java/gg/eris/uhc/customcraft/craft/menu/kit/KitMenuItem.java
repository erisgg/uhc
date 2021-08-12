package gg.eris.uhc.customcraft.craft.menu.kit;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.kit.Kit;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class KitMenuItem implements MenuItem {

  private final Kit kit;

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    CustomCraftUhcPlayer player = viewer.getErisPlayer();

    return this.kit.getDisplay();
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {

  }

}
