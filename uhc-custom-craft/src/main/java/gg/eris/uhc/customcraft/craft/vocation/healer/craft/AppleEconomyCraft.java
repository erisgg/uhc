package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class AppleEconomyCraft extends Craft {

  public AppleEconomyCraft() {
    super("apple_economy", new ItemStack(Material.APPLE, 3));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  @Override
  public int getCraftableAmount() {
    return 2;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 3;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(" m ", " a ", " m ")
        .setIngredient('m', new MaterialData(Material.INK_SACK, DataUtil.BONE_MEAL))
        .setIngredient('a', Material.APPLE);
  }

  @Override
  public String getName() {
    return "Apple Economy";
  }
}
