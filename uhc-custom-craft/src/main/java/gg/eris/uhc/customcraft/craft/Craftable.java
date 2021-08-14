package gg.eris.uhc.customcraft.craft;

import java.util.List;
import org.bukkit.inventory.Recipe;

@FunctionalInterface
public interface Craftable {

  Recipe getRecipe();

  default List<Recipe> getAlternativeRecipes() {
    return List.of();
  }

}
