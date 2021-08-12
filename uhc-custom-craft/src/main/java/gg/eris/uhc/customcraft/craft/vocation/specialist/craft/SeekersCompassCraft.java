package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SeekersCompassCraft extends Craft {

  public SeekersCompassCraft() {
    super("seekers_compass", CraftableInfo.builder()
        .material(Material.COMPASS)
        .color(CC.GREEN)
        .name("Seeker's Compass")
        .quote("All you need is a compass")
        .quoteGiver("Bear Grylls")
        .effects(
            "Right click to find a player",
            "Has two uses"
        )
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
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
            " g ",
            "gag",
            " g"
        )
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('a', Material.APPLE);
  }

  @Override
  public String getName() {
    return "Seeker's Compass";
  }

}
