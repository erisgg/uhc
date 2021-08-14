package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LightAppleCraft extends Craft {

  public LightAppleCraft() {
    super("light_apple", CraftableInfo.builder()
        .material(Material.GOLDEN_APPLE)
        .actual(new ItemStack(Material.GOLDEN_APPLE))
        .color(CC.GREEN)
        .name("Light Apple")
        .quote("This seems familiar...")
        .quoteGiver("UHC Amnesiac")
        .effects("Gives 1 Golden Apple")
        .build()

    );
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
    return new ShapedRecipe(getActualItem())
        .shape(
            " g ",
            "gag",
            " g "
        )
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('a', Material.APPLE);
  }

  @Override
  public String getName() {
    return "Light Apple";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

}
