package gg.eris.uhc.customcraft.craft.vocation.duelist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.TrinketInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ApollosBlessingTrinket extends Trinket {

  public ApollosBlessingTrinket() {
    super("apollos_blessing",
        TrinketInfo.builder()
        .material(Material.FEATHER)
            .color(CC.GOLD)
            .name("Apollo's Blessing")
            .quoteGiver("Apollo")
            .quote("Consider yourself blessed?")
            .effect("10% chance for arrows to automatically aim towards your target")
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " fd",
            "esf",
            "ce "
        ).setIngredient('f', Material.FLINT)
        .setIngredient('d', Material.DIAMOND)
        .setIngredient('e', Material.FEATHER)
        .setIngredient('c', Material.COMPASS)
        .setIngredient('s', Material.STICK);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
  }
}
