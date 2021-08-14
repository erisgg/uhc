package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.Craftable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Craft extends VocationUnlockable implements Craftable, Listener {

  private final ItemStack display;
  private final ItemStack actual;

  public Craft(String identifierValue, CraftableInfo info) {
    super(identifierValue);
    this.display = NBTUtil.setNbtData(info.buildDisplayCraft(), CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY,
        this.getIdentifier().getValue());
    this.actual = info.getActual() == null ? this.display
        : NBTUtil.setNbtData(info.getActual(), CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY,
        this.getIdentifier().getValue());
  }

  public abstract Vocation getVocation();

  public abstract int getCraftableAmount();

  public abstract int getPrestigeCraftableAmount();

  public final ItemStack getDisplayItem() {
    return this.display;
  }

  public final ItemStack getActualItem() {
    return this.actual;
  }

  public final boolean isItem(ItemStack item) {
    if (StackUtil.isNullOrAir(item)) {
      return false;
    }

    String thisData = NBTUtil.getStringNbtData(this.actual,
        CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY);
    String theirData = NBTUtil.getStringNbtData(item,
        CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY);

    if (thisData == null || theirData == null) {
      return false;
    } else {
      return thisData.equals(theirData);
    }
  }
}
