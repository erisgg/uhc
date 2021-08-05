package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SharpnessBookCraft extends Craft {

  public SharpnessBookCraft() {
    super("sharpness_book",
        new ItemBuilder(Material.ENCHANTED_BOOK)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .nonCraftable()
            .nonBrewable()
            .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
  }

  @Override
  public int getCraftableAmount() {
    return 3;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 4;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " l ",
            "psp",
            " p "
        ).setIngredient('l', Material.LEATHER)
        .setIngredient('p', Material.PAPER)
        .setIngredient('s', Material.IRON_SWORD);
  }
}
