package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ExperienceBottleCraft extends Craft {

  public ExperienceBottleCraft() {
    super("experience_bottle", CraftableInfo.builder()
        .color(CC.LIGHT_PURPLE)
        .name("Experience Bottle")
        .quote("Bubble bubble, toil and trouble!")
        .quoteGiver("The Witches")
        .effects("Gives 6 experience bottles")
        .actual(new ItemStack(Material.EXP_BOTTLE, 6))
        .material(Material.EXP_BOTTLE)
        .build()
    );
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
    return new ShapedRecipe(getActualItem())
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
