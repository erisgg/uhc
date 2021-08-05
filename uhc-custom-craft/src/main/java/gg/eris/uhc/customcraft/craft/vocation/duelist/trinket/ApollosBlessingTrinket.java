package gg.eris.uhc.customcraft.craft.vocation.duelist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ApollosBlessingTrinket extends Trinket {

  public ApollosBlessingTrinket() {
    super("apollos_blessing",
        CraftableInfo.builder()
            .material(Material.FEATHER)
            .color(CC.GOLD)
            .name("Apollo's Blessing")
            .quote("Consider yourself blessed?")
            .quoteGiver("Apollo")
            .effects("10% chance for arrows to automatically aim towards your target")
            .nonTransformable()
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

  @Override
  public String getName() {
    return "Apollo's Blessing";
  }

}
