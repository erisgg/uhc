package gg.eris.uhc.customcraft.craft.vocation;

import com.google.common.collect.Sets;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.Unlockable;
import java.util.Set;
import lombok.Getter;
import org.bukkit.inventory.Recipe;

public abstract class VocationRegistry extends Registry<Unlockable> {

  private static final IdentifierProvider VOCATION_REGISTRY_PROVIDER = new IdentifierProvider("vocation");

  @Getter
  protected final Identifier identifier;

  public VocationRegistry() {
    this.identifier = VOCATION_REGISTRY_PROVIDER.id(getIdentifierValue());
  }

  public final Set<Recipe> getRecipes() {
    Set<Recipe> recipes = Sets.newHashSet();
    for (Unlockable unlockable : this.values()) {
      if (unlockable instanceof Craftable) {
        recipes.add(((Craftable) unlockable).getRecipe());
      }
    }

    return recipes;
  }

  public abstract String getIdentifierValue();

  public abstract Perk getPerk();

  public abstract Trinket getFirstTrinket();

  public abstract Trinket getSecondTrinket();

  public abstract Craft getFirstCraft();

  public abstract Craft getSecondCraft();

  public abstract Craft getThirdCraft();

  public abstract Craft getFourthCraft();

}
