package gg.eris.uhc.customcraft.craft;

import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class Craft extends Unlockable implements Craftable, Listener {

  private final ItemStack item;

  public Craft(String identifierValue, CraftableInfo info) {
    super(identifierValue);
    this.item = info.buildCraft();
  }

  public Craft(String identifierValue, ItemStack item) {
    super(identifierValue);
    this.item = item;
  }

  public abstract Vocation getVocation();

  public abstract int getCraftableAmount();

  public abstract int getPrestigeCraftableAmount();

  public final ItemStack getItem() {
    return this.item;
  }
}
