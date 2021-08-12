package gg.eris.uhc.customcraft.craft.vocation.extractor.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ArtemisQuiverTrinket extends Trinket {

  public ArtemisQuiverTrinket() {
    super("artemis_quiver", CraftableInfo.builder()
        .material(Material.EYE_OF_ENDER)
        .name("Artemis' Quiver")
        .color(CC.LIGHT_PURPLE)
        .quote("One shot, one kill")
        .quoteGiver("Artemis")
        .effects(
            "Arrows have a 20% chance to return after being fired"
        ).nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "ff ",
            "fsb",
            " bR")
        .setIngredient('f', Material.FLINT)
        .setIngredient('s', Material.STICK)
        .setIngredient('b', Material.BONE)
        .setIngredient('R', Material.REDSTONE_BLOCK);
  }

  @Override
  public String getName() {
    return "Artemis' Quiver";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }

}
