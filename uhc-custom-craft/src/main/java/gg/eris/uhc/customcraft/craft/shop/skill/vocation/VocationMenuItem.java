package gg.eris.uhc.customcraft.craft.shop.skill.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.Unlockable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class VocationMenuItem implements MenuItem {

  private static final ItemStack CRAFTS = new ItemStack(Material.STAINED_GLASS, 1, DataUtil.PURPLE);
  private static final ItemStack PERKS = new ItemStack(Material.STAINED_GLASS, 1, DataUtil.RED);
  private static final ItemStack TRINKETS = new ItemStack(Material.STAINED_GLASS, 1,
      DataUtil.LIGHT_BLUE);
  private static final ItemStack UNLOCKED = new ItemStack(Material.STAINED_GLASS, 1, DataUtil.LIME);

  private final Unlockable unlockable;
  private final ItemStack locked;
  private final ItemStack unlocked;

  public VocationMenuItem(Unlockable unlockable) {
    this.unlockable = unlockable;

    if (unlockable.getVocation().getRegistry() == null) {
      this.locked = null;
      this.unlocked = null;
      return;
    }

    // Building the items
    if (unlockable instanceof Craft) {
      Craft craft = (Craft) unlockable;
      this.locked = new ItemBuilder(CRAFTS)
          .withName(
              CC.WHITE.bold().underline() + "CRAFT:" + CC.LIGHT_PURPLE + " " + unlockable.getName())
          .withLore((craft.getItem().getItemMeta().hasLore() ?
              craft.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0])
          ).build();
      this.unlocked = new ItemBuilder(UNLOCKED.clone())
          .withName(
              CC.WHITE.bold().underline() + "CRAFT:" + CC.LIGHT_PURPLE + " " + unlockable.getName())
          .withLore((craft.getItem().getItemMeta().hasLore() ?
              craft.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0])
          ).build();
    } else if (unlockable instanceof Trinket) {
      Trinket trinket = (Trinket) unlockable;
      this.locked = new ItemBuilder(TRINKETS)
          .withName(CC.WHITE.bold().underline() + "TRINKET:" + CC.AQUA + " " + unlockable.getName())
          .withLore((trinket.getItem().getItemMeta().hasLore() ?
              trinket.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0])
          ).build();
      this.unlocked = new ItemBuilder(UNLOCKED.clone())
          .withName(CC.WHITE.bold().underline() + "TRINKET:" + CC.AQUA + " " + unlockable.getName())
          .withLore((trinket.getItem().getItemMeta().hasLore() ?
              trinket.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0])
          ).build()
      ;
    } else {
      Perk perk = (Perk) unlockable;

      List<String> lore = new ArrayList<>(List.of(
          "",
          CC.GOLD.bold().underline() + "EFFECT",
          ""
      ));

      List<String> description = Text.splitWords(perk.getDescription(1), 6);
      for (int i = 0; i < description.size(); i++) {
        if (i == 0) {
          lore.add(CC.WHITE + " - " + description.get(i));
        } else {
          lore.add(CC.WHITE + "   " + description.get(i));
        }
      }

      String[] loreArray = lore.toArray(new String[0]);

      this.locked = new ItemBuilder(PERKS)
          .withName(CC.WHITE.bold().underline() + "PERK:" + CC.RED + " " + unlockable.getName())
          .withLore(loreArray)
          .build();

      this.unlocked = new ItemBuilder(UNLOCKED)
          .withName(CC.WHITE.bold().underline() + "PERK:" + CC.RED + " " + unlockable.getName())
          .withLore(loreArray)
          .build();
    }
  }

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    return this.locked;
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {

  }

}
