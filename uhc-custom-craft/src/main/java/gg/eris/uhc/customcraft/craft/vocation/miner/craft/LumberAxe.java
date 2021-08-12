package gg.eris.uhc.customcraft.craft.vocation.miner.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LumberAxe extends Craft {

  public LumberAxe() {
    super("lumber_axe", CraftableInfo.builder()
        .material(Material.IRON_AXE)
        .color(CC.DARK_BLUE)
        .name("Lumber Axe")
        .quote("What's that snapping sound?")
        .quoteGiver("(Dead) Lumberjack")
        .effects("Instantly chops down trees")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
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
            "fff",
            "lal",
            "   "
        ).setIngredient('f', Material.FLINT)
        .setIngredient('l', Material.LOG)
        .setIngredient('a', Material.IRON_AXE);
  }

  @Override
  public String getName() {
    return "Lumber Axe";
  }
}
