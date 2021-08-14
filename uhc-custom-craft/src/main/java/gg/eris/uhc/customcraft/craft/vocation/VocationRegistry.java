package gg.eris.uhc.customcraft.craft.vocation;

import com.google.common.collect.Sets;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.menu.shop.vocation.VocationMenu;
import java.util.Set;
import lombok.Getter;
import org.bukkit.inventory.Recipe;

public abstract class VocationRegistry extends Registry<VocationUnlockable> {

  private static final IdentifierProvider VOCATION_REGISTRY_PROVIDER
      = new IdentifierProvider("vocation");

  @Getter
  protected final Identifier identifier;

  public VocationRegistry() {
    this.identifier = VOCATION_REGISTRY_PROVIDER.id(getIdentifierValue());
  }

  public final Set<Recipe> getRecipes() {
    Set<Recipe> recipes = Sets.newHashSet();
    for (VocationUnlockable unlockable : this.values()) {
      if (unlockable instanceof Craftable) {
        Craftable craftable = (Craftable) unlockable;
        Recipe recipe = craftable.getRecipe();
        if (recipe != null) {
          recipes.add(recipe);
          recipes.addAll(craftable.getAlternativeRecipes());
        }
      }
    }

    return recipes;
  }

  public final VocationUnlockable getUnlockableFromMenuSlot(int slot) {
    switch (slot) {
      case VocationMenu.FIRST_CRAFT_SLOT:
        return getFirstCraft();
      case VocationMenu.SECOND_CRAFT_SLOT:
        return getSecondCraft();
      case VocationMenu.THIRD_CRAFT_SLOT:
        return getThirdCraft();
      case VocationMenu.FOURTH_CRAFT_SLOT:
        return getFourthCraft();
      case VocationMenu.FIRST_TRINKET_SLOT:
        return getFirstTrinket();
      case VocationMenu.SECOND_TRINKET_SLOT:
        return getSecondTrinket();
      default:
        if (VocationMenu.PERK_SLOTS_SET.contains(slot)) {
          return getPerk();
        }
        return null;
    }
  }

  public abstract String getIdentifierValue();

  public abstract Perk getPerk();

  public abstract Trinket getFirstTrinket();

  public abstract Trinket getSecondTrinket();

  public abstract Craft getFirstCraft();

  public abstract Craft getSecondCraft();

  public abstract Craft getThirdCraft();

  public abstract Craft getFourthCraft();

  public abstract Vocation getVocation();

}
