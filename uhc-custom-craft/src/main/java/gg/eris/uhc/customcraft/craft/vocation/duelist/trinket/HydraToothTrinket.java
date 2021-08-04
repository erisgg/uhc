package gg.eris.uhc.customcraft.craft.vocation.duelist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.TrinketInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HydraToothTrinket extends Trinket {

  public HydraToothTrinket() {
    super("hydra_tooth", TrinketInfo.builder()
        .color(CC.DARK_PURPLE)
        .effect("Increases sword damage by 0.5")
        .name("Hydra Tooth")
        .quote("Ouch.")
        .quoteGiver("The Hydra")
        .material(Material.GHAST_TEAR)
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " dI",
            "fSd",
            "ff "
        ).setIngredient('d', Material.DIAMOND)
        .setIngredient('f', Material.FLINT)
        .setIngredient('S', Material.IRON_SWORD)
        .setIngredient('I', Material.IRON_BLOCK);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
  }

}