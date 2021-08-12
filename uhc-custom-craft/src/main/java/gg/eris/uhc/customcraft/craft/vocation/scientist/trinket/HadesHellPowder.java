package gg.eris.uhc.customcraft.craft.vocation.scientist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HadesHellPowder extends Trinket {

  public HadesHellPowder() {
    super("hades_hell_powder", CraftableInfo.builder()
        .material(Material.FIREWORK_CHARGE)
        .color(CC.DARK_RED)
        .name("Hades' Hell Powder")
        .quote("I'm not giving you a quote.")
        .quoteGiver("Hades")
        .effects(
            "All nether mobs have a 10% chance to drop nether warts"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "qfq",
            "ngn",
            "qbq")
        .setIngredient('q', Material.QUARTZ)
        .setIngredient('n', Material.NETHERRACK)
        .setIngredient('g', Material.SULPHUR)
        .setIngredient('b', Material.BONE);
  }

  @Override
  public String getName() {
    return "Hades' Hell Powder";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }
}
