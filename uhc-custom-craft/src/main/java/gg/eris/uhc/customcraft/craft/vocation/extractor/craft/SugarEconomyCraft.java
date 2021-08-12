package gg.eris.uhc.customcraft.craft.vocation.extractor.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SugarEconomyCraft extends Craft {

  // new ItemStack(Material.SUGAR_CANE, 4)

  public SugarEconomyCraft() {
    super("sugar_economy", CraftableInfo.builder()
        .color(CC.BLUE)
        .name("Cane Economy")
        .quote("I'm Lovin' It.")
        .quoteGiver("Some restaurant")
        .effects("Gives 4 sugar cane")
        .base(new ItemStack(Material.SUGAR_CANE, 4))
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
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
        .shape(" s ", "scs", " s ")
        .setIngredient('s', Material.SEEDS)
        .setIngredient('c', Material.SUGAR_CANE);
  }

  @Override
  public String getName() {
    return "x4 Sugar Cane";
  }
}
