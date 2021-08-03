package gg.eris.uhc.customcraft.craft.menu;

import gg.eris.commons.bukkit.menu.MenuViewer;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public final class CraftingMenuViewer extends MenuViewer {

  private final Int2ObjectMap<ItemStack> items;

  public CraftingMenuViewer(Player player) {
    super(player);
    this.items = new Int2ObjectArrayMap<>();
  }



}
