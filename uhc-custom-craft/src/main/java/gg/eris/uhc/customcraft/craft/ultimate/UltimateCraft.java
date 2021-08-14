package gg.eris.uhc.customcraft.craft.ultimate;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.inventory.ItemStack;

public abstract class UltimateCraft implements Identifiable {

  public static final String NBT_KEY = "ultimate_unlockable";

  private final Identifier identifier;
  private final ItemStack item;

  public UltimateCraft(String identifierValue, CraftableInfo info) {
    this.identifier = CustomCraftUhcIdentifiers.ULTIMATE_UNLOCKABLE.id(identifierValue);
    this.item = NBTUtil.setNbtData(info.buildUltimateCraft(), NBT_KEY, this.identifier.getValue());
  }

  public abstract String getName();

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

  public final ItemStack getItem() {
    return this.item;
  }

  public final boolean isItem(ItemStack item) {
    String data = NBTUtil.getStringNbtData(this.item, NBT_KEY);
    String data2 = NBTUtil.getStringNbtData(item, NBT_KEY);
    return data.equals(data2);
  }

  public static Identifier getIdentifierFromItemStack(ItemStack item) {
    return Identifier.fromString(NBTUtil.getStringNbtData(item, NBT_KEY));
  }

}
