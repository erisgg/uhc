package gg.eris.uhc.customcraft.craft.craft;

import gg.eris.uhc.customcraft.craft.recipe.ShapedCraftingRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TestCraft extends ShapedCraftingRecipe {

  public TestCraft() {
    super((player) -> new ItemStack(Material.GOLD_NUGGET),
        new ShapedCraftingRecipe.MaterialBuilder()
            .shape("a  ",
                "a  ",
                "a  ")
            .setIngredient('a', Material.BEDROCK)
            .build()
    );
  }
}
