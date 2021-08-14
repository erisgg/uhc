package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

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

public final class AppleEconomyCraft extends Craft {

  public AppleEconomyCraft() {
    super("apple_economy", CraftableInfo.builder()
        .color(CC.RED)
        .name("Apple Economy")
        .quote("Why buy business when you can buy bitcoin?")
        .quoteGiver("WSB Investor")
        .effects("Gives 3 apples")
        .actual(new ItemStack(Material.APPLE, 3))
        .material(Material.APPLE)
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
    return new ShapedRecipe(getActualItem())
        .shape(" m ", " a ", " m ")
        .setIngredient('m', new MaterialData(Material.INK_SACK, DataUtil.BONE_MEAL))
        .setIngredient('a', Material.APPLE);
  }

  @Override
  public String getName() {
    return "Apple Economy";
  }
}
