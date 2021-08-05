package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class SpeedSoupCraft extends Craft {

  public SpeedSoupCraft() {
    super("speed_soup", CraftableInfo.builder()
        .material(Material.MUSHROOM_SOUP)
        .color(CC.GREEN)
        .name("Speed Soup")
        .quote("Zoom zoom zoom!")
        .quoteGiver("Sheldon Cooper as The Flash")
        .effects("Instantly heals you 3 hearts", "Gives you Speed I for 10 seconds")
        .nonTransformable()
        .build()
    );
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
        .shape(
            "gsg",
            "cbc",
            "   "
        ).setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('s', Material.SUGAR)
        .setIngredient('c', Material.SUGAR_CANE)
        .setIngredient('b', Material.MUSHROOM_SOUP);
  }

  @Override
  public String getName() {
    return "Speed Soup";
  }
}
