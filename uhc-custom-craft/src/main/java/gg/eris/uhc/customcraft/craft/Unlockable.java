package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Unlockable implements Identifiable {

  public static final String NBT_KEY = "unlockable";

  private final Identifier identifier;

  public Unlockable(String identifierValue) {
    this.identifier = CustomCraftUhcIdentifiers.UNLOCKABLE.id(identifierValue);
  }

  public abstract String getName();

  public abstract Vocation getVocation();

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

  public static Identifier getIdentifierFromItemStack(ItemStack item) {
    return Identifier.fromString(NBTUtil.getStringNbtData(item, NBT_KEY));
  }

}
