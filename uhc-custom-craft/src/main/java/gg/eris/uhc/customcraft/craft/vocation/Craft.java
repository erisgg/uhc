package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.craft.Craftable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Craft extends VocationUnlockable implements Craftable, Listener {

  private final ItemStack item;

  public Craft(String identifierValue, CraftableInfo info) {
    this(identifierValue, info.buildCraft());
  }

  public Craft(String identifierValue, ItemStack item) {
    super(identifierValue);
    this.item = NBTUtil.setNbtData(item, NBT_KEY, this.getIdentifier().getValue());
  }

  public abstract Vocation getVocation();

  public abstract int getCraftableAmount();

  public abstract int getPrestigeCraftableAmount();

  public final ItemStack getItem() {
    return this.item;
  }

  public final boolean isItem(ItemStack item) {
    String data = NBTUtil.getStringNbtData(this.item, NBT_KEY);
    String data2 = NBTUtil.getStringNbtData(item, NBT_KEY);
    return data.equals(data2);
  }
}
