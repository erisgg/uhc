package gg.eris.uhc.customcraft.craft;

import org.bukkit.inventory.Recipe;

@FunctionalInterface
public interface Craftable {

  Recipe getRecipe();

}
