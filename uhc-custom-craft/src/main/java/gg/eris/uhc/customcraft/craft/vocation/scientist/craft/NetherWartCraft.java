package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class NetherWartCraft extends Craft {

  public NetherWartCraft() {
    super("nether_wart", new ItemStack(Material.NETHER_STALK));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
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
            "rsr",
            "ses",
            "msm"
        )
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('s', Material.SEEDS)
        .setIngredient('e', Material.SPIDER_EYE)
        .setIngredient('m', Material.BROWN_MUSHROOM);
  }

  @Override
  public String getName() {
    return "Nether Wart";
  }
}
