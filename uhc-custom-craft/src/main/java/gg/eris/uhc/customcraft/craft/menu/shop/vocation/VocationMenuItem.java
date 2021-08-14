package gg.eris.uhc.customcraft.craft.menu.shop.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class VocationMenuItem implements MenuItem {

  private static final short CRAFTS = DataUtil.PURPLE;
  private static final short PERKS = DataUtil.RED;
  private static final short TRINKETS = DataUtil.LIGHT_BLUE;
  private static final short UNLOCKED = DataUtil.LIME;

  private final VocationUnlockable unlockable;
  private final Vocation vocation;
  private final int slot;
  private final IntSet prerequisiteSlots;
  private final ItemStack purchasable;
  private final ItemStack locked;
  private final ItemStack unlocked;
  private final int price;

  public VocationMenuItem(VocationUnlockable unlockable, int price, int slot,
      int... prerequisiteSlots) {
    this.unlockable = unlockable;
    this.vocation = unlockable.getVocation();
    this.slot = slot;
    this.prerequisiteSlots = IntSets.unmodifiable(new IntArraySet(prerequisiteSlots));
    this.price = price;

    if (unlockable.getVocation().getRegistry() == null) {
      throw new IllegalArgumentException("Invalid shop item for unlockable " + unlockable);
    }

    ItemBuilder base = new ItemBuilder(Material.STAINED_GLASS);

    // Building the items
    if (unlockable instanceof Craft) {
      Craft craft = (Craft) unlockable;
      base
          .withName(
              CC.WHITE.bold().underline() + "CRAFT:" + CC.LIGHT_PURPLE + " " + unlockable.getName())
          .withLore((craft.getItem().getItemMeta().hasLore() ?
              craft.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0]))
          .withDurability(CRAFTS);
    } else if (unlockable instanceof Trinket) {
      Trinket trinket = (Trinket) unlockable;
      base.withName(CC.WHITE.bold().underline() + "TRINKET:" + CC.AQUA + " " + unlockable.getName())
          .withLore((trinket.getItem().getItemMeta().hasLore() ?
              trinket.getItem().getItemMeta().getLore().toArray(new String[0]) : new String[0])
          ).withDurability(TRINKETS);
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

      base.withName(CC.WHITE.bold().underline() + "PERK:" + CC.RED + " " + unlockable.getName())
          .withLore(loreArray)
          .withDurability(PERKS);
    }

    this.unlocked = new ItemBuilder(base.build())
        .withDurability(UNLOCKED)
        .addLore(
            "",
            CC.GREEN.bold() + "UNLOCKED"
        ).build();

    this.locked = new ItemBuilder(base.build())
        .addLore(
            "",
            CC.RED.bold() + "LOCKED"
        ).build();

    this.purchasable = new ItemBuilder(base.build()).addLore(
        "",
        CC.GOLD.bold() + "CLICK TO PURCHASE (" + Text.formatInt(this.price) + " COINS)"
    ).build();
  }

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    CustomCraftUhcPlayer customCraftUhcPlayer = viewer.getErisPlayer();
    if (customCraftUhcPlayer == null) {
      return this.locked;
    }

    if (customCraftUhcPlayer.hasSlot(this.vocation, this.slot)) {
      return this.unlocked;
    } else {
      if (hasPrerequisite(customCraftUhcPlayer)) {
        return this.purchasable;
      }
      return this.locked;
    }
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    CustomCraftUhcPlayer player = viewer.getErisPlayer();
    if (hasPrerequisite(player) && !player.hasSlot(this.vocation, this.slot)) {
      if (player.getCoins() < this.price) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You do not have enough coins to buy <h>{0}</h>! You need <h>{1}</h> more coins.",
            this.unlockable.getName(),
            Text.formatInt(this.price - player.getCoins())
        );
      } else {
        player.addTreeData(this.vocation, this.slot);
        player.addUnlockable(this.unlockable);
        player.spendCoins(this.price);
        TextController.send(
            player.getHandle(),
            TextType.SUCCESS,
            "You have bought the <h>{0}</h> (-<h>{1}</h> coins).",
            this.unlockable.getName(),
            Text.formatInt(this.price)
        );
        viewer.getViewing().updateMenu(viewer);
      }
    }
  }

  private boolean hasPrerequisite(CustomCraftUhcPlayer player) {
    if (this.prerequisiteSlots.isEmpty()) {
      return true;
    }

    for (int prerequisite : this.prerequisiteSlots) {
      if (!player.hasSlot(this.vocation, prerequisite)) {
        return false;
      }
    }
    return true;
  }

}
