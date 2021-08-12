package gg.eris.uhc.customcraft.craft.vocation.scientist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ChronosClock extends Trinket {

  public ChronosClock() {
    super("chronos_clock", CraftableInfo.builder()
        .material(Material.WATCH)
        .color(CC.DARK_PURPLE)
        .name("Chronos' Clock")
        .quote(".emit tuoba")
        .quoteGiver("Chronos")
        .effects(
            "Adds 10 seconds to potion effect duration"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "geg",
            "pbp",
            "gGg"
        )
        .setIngredient('g', Material.GLOWSTONE_DUST)
        .setIngredient('e', Material.EMERALD)
        .setIngredient('p', Material.BLAZE_POWDER)
        .setIngredient('b', Material.GLASS_BOTTLE)
        .setIngredient('G', Material.GOLD_INGOT);
  }

  @Override
  public String getName() {
    return "Chronos's Clock";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }
}
