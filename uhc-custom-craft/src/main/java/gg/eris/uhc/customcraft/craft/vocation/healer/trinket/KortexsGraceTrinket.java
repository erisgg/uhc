package gg.eris.uhc.customcraft.craft.vocation.healer.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class KortexsGraceTrinket extends Trinket {

  public KortexsGraceTrinket() {
    super("kortexs_grace", CraftableInfo.builder()
        .material(Material.NETHER_STAR)
        .color(CC.GOLD)
        .name("Kortex's Grace")
        .quote("Who is Kortex?")
        .quoteGiver("Kortex")
        .effects("Final heal at the end of the grace period")
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " i ",
            "igi",
            " i "
        ).setIngredient('i', Material.IRON_INGOT)
        .setIngredient('g', Material.GOLD_INGOT);
  }

  @Override
  public String getName() {
    return "Kortex's Grace";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }
}
