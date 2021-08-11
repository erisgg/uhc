package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class AbsorberCraft extends Craft {

  public AbsorberCraft() {
    super("absorber", CraftableInfo.builder()
        .material(Material.IRON_AXE)
        .color(CC.GREEN)
        .name("Absorber")
        .quote("OM NOM.")
        .quoteGiver("Kirby")
        .effects("Gives absorption 2 for 1:30")
        .nonTransformable()
        .build());
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
            " R ",
            "gbg",
            " L "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('b', Material.GLASS_BOTTLE)
        .setIngredient('L', Material.LAPIS_BLOCK);
  }

  @Override
  public String getName() {
    return "Absorber";
  }
}
