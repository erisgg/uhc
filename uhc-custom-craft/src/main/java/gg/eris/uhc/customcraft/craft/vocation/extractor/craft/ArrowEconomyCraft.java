package gg.eris.uhc.customcraft.craft.vocation.extractor.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ArrowEconomyCraft extends Craft {

  public ArrowEconomyCraft() {
    super("arrow_economy", CraftableInfo.builder()
        .base(new ItemStack(Material.ARROW, 16))
        .color(CC.BLUE)
        .name("Arrow Economy")
        .quote("Every little helps!")
        .quoteGiver("Superstore")
        .effects("Gives 16 arrows")
        .actual(new ItemStack(Material.ARROW, 16))
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
    return new ShapedRecipe(getActualItem())
        .shape(
            " ff",
            "esf",
            "ee "
        ).setIngredient('f', Material.FLINT)
        .setIngredient('e', Material.FEATHER)
        .setIngredient('s', Material.STICK);
  }

  @Override
  public String getName() {
    return "Arrow Economy";
  }
}
