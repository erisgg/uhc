package gg.eris.uhc.customcraft.craft.vocation.enchanter.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class IrisEnchantment extends Trinket {

  public IrisEnchantment() {
    super("iris_enchantment", CraftableInfo.builder()
        .material(Material.DIAMOND)
        .name("Iris' Enchantment")
        .color(CC.AQUA)
        .quote("Let it shine!")
        .quoteGiver("Take That")
        .effects(
            "When using an anvil or enchantment table, 35% of experience used is returned"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "LRL",
            "nbn",
            "eee")
        .setIngredient('L', Material.LAPIS_BLOCK)
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('n', Material.GOLD_NUGGET)
        .setIngredient('b', Material.BOOK)
        .setIngredient('e', Material.EXP_BOTTLE);
  }

  @Override
  public String getName() {
    return "Iris' Enchantment";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }
}
