package gg.eris.uhc.customcraft.craft.bag;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public final class TrinketBag {

  protected static final IntList INVENTORY_SLOTS =
      IntLists.unmodifiable(new IntArrayList(List.of(11, 13, 15)));

  protected static final IntSet INVENTORY_SLOTS_SET =
      IntSets.unmodifiable(new IntArraySet(Set.of(11, 13, 15)));

  protected static final ItemStack EMPTY_SLOT = new ItemBuilder(Menu.LIGHT_FILLER.clone())
      .withName(CC.RED + "No Trinket")
      .build();

  protected static final ItemStack ITEM = new ItemBuilder(Material.FLOWER_POT_ITEM)
      .withName(CC.BLUE + "Trinket Bag " + CC.GRAY + "(Right Click)")
      .withLore(CC.GRAY.italic() + "Right click to open your trinket bag")
      .build();

  private final CustomCraftUhcPlayer player;
  private final Trinket[] contents;

  public TrinketBag(CustomCraftUhcPlayer player) {
    this.player = player;
    this.contents = new Trinket[3];
  }

  protected void fillInventory(Inventory inventory) {
    for (int i = 0; i < 9; i++) {
      inventory.setItem(i, Menu.DARK_FILLER);
      inventory.setItem(i + 18, Menu.DARK_FILLER);
      int plusNine = i + 9;
      if (!INVENTORY_SLOTS.contains(plusNine)) {
        inventory.setItem(plusNine, Menu.DARK_FILLER);
      }
    }

    for (int i = 0; i < 3; i++) {
      Trinket trinket = this.contents[i];
      int slot = INVENTORY_SLOTS.getInt(i);
      if (trinket == null) {
        inventory.setItem(slot, EMPTY_SLOT);
      } else {
        inventory.setItem(slot, trinket.getItem());
      }
    }
  }

  public Trinket getTrinket(int index) {
    return this.contents[index];
  }

  public void addTrinket(Trinket trinket, int index) {
    Validate.isNull(this.contents[index], "cannot overwrite trinket");
    this.contents[index] = trinket;
  }

  public Trinket removeTrinket(int index) {
    Trinket old = this.contents[index];
    this.contents[index] = null;
    return old;
  }

  public boolean hasTrinket(Trinket trinket) {
    if (trinket == null) {
      return false;
    }

    for (Trinket other : this.contents) {
      if (trinket == other) {
        return true;
      }
    }

    return false;
  }

  public static boolean isBag(ItemStack item) {
    return ITEM.isSimilar(item);
  }

  public static void giveBag(Player player) {
    player.getInventory().addItem(ITEM);
  }

}
