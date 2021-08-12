package gg.eris.uhc.customcraft.craft.vocation.extractor.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SaddleCraft extends Craft {

  public SaddleCraft() {
    super("saddle", CraftableInfo.builder()
        .material(Material.SADDLE)
        .color(CC.GRAY)
        .name("Saddle")
        .quote("From the Old Town Road")
        .quoteGiver("Lil Nas X")
        .effects("Gives a saddle")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
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
            "bsb",
            "lll",
            "lsl"
        ).setIngredient('b', Material.BONE)
        .setIngredient('s', Material.STRING)
        .setIngredient('l', Material.LEATHER);
  }

  @Override
  public String getName() {
    return "Saddle Craft";
  }
}
