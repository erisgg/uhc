package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import org.bukkit.inventory.ItemStack;

public abstract class VocationUnlockable implements Identifiable {

  private final Identifier identifier;

  public VocationUnlockable(String identifierValue) {
    this.identifier = CustomCraftUhcIdentifiers.UNLOCKABLE.id(identifierValue);
  }

  public abstract String getName();

  public abstract Vocation getVocation();

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

  public static Identifier getIdentifierFromItemStack(ItemStack item) {
    return Identifier.fromString(
        NBTUtil.getStringNbtData(item, CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY));
  }

}
