package gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DefendersMedallion extends Trinket {

  public DefendersMedallion() {
    super("defenders_medallion", CraftableInfo.builder()
        .material(Material.YELLOW_FLOWER)
        .color(CC.GOLD)
        .name("Defender's Medallion")
        .quote("Those who are defended are stronger!")
        .quoteGiver("Anicetus")
        .effects("Gives a 50% chance to reduce damage taken by 20%")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "iii",
            "iGi",
            "iii"
        ).setIngredient('i', Material.IRON_INGOT)
        .setIngredient('G', Material.GOLD_BLOCK);
  }

  @Override
  public String getName() {
    return "Defender's Medallion";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }
}
