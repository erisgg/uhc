package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LightAppleCraft extends Craft {

  public LightAppleCraft() {
    super("light_apple", new ItemStack(Material.GOLDEN_APPLE));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  @Override
  public int getCraftableAmount() {
    return 1;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 2;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " g ",
            "gag",
            " g"
        )
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('a', Material.APPLE);
  }

  @Override
  public String getName() {
    return "Light Apple";
  }

}
