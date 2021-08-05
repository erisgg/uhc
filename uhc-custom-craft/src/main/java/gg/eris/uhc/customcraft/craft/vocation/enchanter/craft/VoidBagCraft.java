package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class VoidBagCraft extends Craft {

  public VoidBagCraft() {
    super("void_bag", CraftableInfo.builder()
        .material(Material.ENDER_CHEST)
        .color(CC.BLACK)
        .name("Void Bag")
        .quote("*static*")
        .quoteGiver("Void Astronaut")
        .effects("When opened, gives you a random item")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
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
            "lol",
            "oco",
            "eee"
        ).setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('o', Material.OBSIDIAN)
        .setIngredient('c', Material.CHEST)
        .setIngredient('e', Material.EXP_BOTTLE);
  }

  @Override
  public String getName() {
    return "Void Bag";
  }
}
