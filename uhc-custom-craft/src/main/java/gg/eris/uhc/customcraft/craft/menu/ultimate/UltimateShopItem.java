package gg.eris.uhc.customcraft.craft.menu.ultimate;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class UltimateShopItem implements MenuItem {

  private static final ItemStack UNKNOWN =
      new ItemBuilder(Menu.LIGHT_FILLER).withName(CC.RED.bold() + "???").withLore("",
          CC.DARK_RED.bold() + "UNDISCOVERED").build();

  private final UltimateCraft ultimate;

  private final ItemStack locked;

  public UltimateShopItem(UltimateCraft ultimate) {
    this.ultimate = ultimate;
    this.locked = ultimate == null ? null : new ItemBuilder(this.ultimate.getItem())
        .addLore("", CC.RED.bold() + "LOCKED").build();
  }

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    if (this.ultimate == null) {
      return UNKNOWN;
    } else {
      return this.locked;
    }
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    TextController.send(
        viewer.getPlayer(),
        TextType.ERROR,
        "Ultimates are currently <h>locked</h> at this stage of the BETA to perfect regular balance!"
    );
  }
}
