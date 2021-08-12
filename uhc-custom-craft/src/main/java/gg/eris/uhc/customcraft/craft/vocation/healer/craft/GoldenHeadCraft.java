package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class GoldenHeadCraft extends Craft {

  public GoldenHeadCraft() {
    // TODO
    super("golden_head", new ItemStack(Material.SKULL_ITEM, 1));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  @Override
  public int getCraftableAmount() {
    return 3;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 4;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape("ggg", "gsg", "ggg")
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('s', Material.SKULL_ITEM);
  }

  @Override
  public String getName() {
    return "Golden Head";
  }
}
