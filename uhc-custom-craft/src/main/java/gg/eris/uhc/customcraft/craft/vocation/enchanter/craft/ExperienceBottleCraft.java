package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ExperienceBottleCraft extends Craft {

  public ExperienceBottleCraft() {
    super("experience_bottle", new ItemStack(Material.EXP_BOTTLE, 6));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
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
        .shape(" t ", "lbl", " l ")
        .setIngredient('l', Material.LAPIS_BLOCK)
        .setIngredient('b', Material.GLASS_BOTTLE)
        .setIngredient('t', Material.REDSTONE_TORCH_ON);
  }

  @Override
  public String getName() {
    return "6x Experience Bottle";
  }
}
