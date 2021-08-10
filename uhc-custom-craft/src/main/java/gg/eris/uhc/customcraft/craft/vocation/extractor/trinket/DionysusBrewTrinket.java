package gg.eris.uhc.customcraft.craft.vocation.extractor.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class DionysusBrewTrinket extends Trinket {

  public DionysusBrewTrinket() {
    super("dionysus_brew", CraftableInfo.builder()
        .material(Material.ROTTEN_FLESH)
        .name("Dionysus' Brew")
        .color(CC.DARK_GREEN)
        .quote("Don't drink it.")
        .quoteGiver("Dionysus")
        .effects(
            "50% chance for mobs to double their loot drops"
        ).nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " r ",
            "bfb",
            " b "
        ).setIngredient('r', Material.ROTTEN_FLESH)
        .setIngredient('b', new MaterialData(Material.INK_SACK, DataUtil.BONE_MEAL))
        .setIngredient('f', Material.FLINT_AND_STEEL);
  }

  @Override
  public String getName() {
    return "Dionysus' Brew";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }
}
