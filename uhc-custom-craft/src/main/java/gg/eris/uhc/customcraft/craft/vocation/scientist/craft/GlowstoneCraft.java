package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class GlowstoneCraft extends Craft {

  public GlowstoneCraft() {
    super("glowstone_craft", CraftableInfo.builder()
        .color(CC.YELLOW)
        .name("Glowstone Dust")
        .effects("Gives 6 Glowstone Dust")
        .quote("Let it shine!")
        .quoteGiver("Take That")
        .material(Material.GLOWSTONE_DUST)
        .actual(new ItemStack(Material.GLOWSTONE_DUST, 6))
        .build()
    );
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
    return new ShapedRecipe(getActualItem())
        .shape(" d ", "drd", " d ")
        .setIngredient('d', new MaterialData(Material.INK_SACK, DataUtil.YELLOW_DYE))
        .setIngredient('r', Material.REDSTONE);
  }

  @Override
  public String getName() {
    return "Glowstone";
  }
}
