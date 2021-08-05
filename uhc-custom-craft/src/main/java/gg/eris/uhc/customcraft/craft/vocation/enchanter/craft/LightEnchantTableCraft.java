package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LightEnchantTableCraft extends Craft {

  public LightEnchantTableCraft() {
    super("light_enchant_table", new ItemStack(Material.ENCHANTMENT_TABLE));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }

  @Override
  public int getCraftableAmount() {
    return 1;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 2;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(" d ", " b ", " o ")
        .setIngredient('d', Material.DIAMOND)
        .setIngredient('b', Material.BOOKSHELF)
        .setIngredient('o', Material.OBSIDIAN);
  }

  @Override
  public String getName() {
    return "Light Enchant Table";
  }
}
