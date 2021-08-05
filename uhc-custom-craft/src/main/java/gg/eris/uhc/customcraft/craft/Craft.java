package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Craft extends Unlockable implements Craftable, Listener {

  private final ItemStack item;

  public Craft(String identifierValue, CraftableInfo info) {
    this(identifierValue, info.buildCraft());
  }

  public Craft(String identifierValue, ItemStack item) {
    super(identifierValue);
    this.item = NBTUtil.setNbtData(item, NBT_KEY, this.getIdentifier().toString());
  }

  public abstract Vocation getVocation();

  public abstract int getCraftableAmount();

  public abstract int getPrestigeCraftableAmount();

  public final ItemStack getItem() {
    return this.item;
  }
}
