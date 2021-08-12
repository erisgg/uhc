package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public final class LightObsidianCraft extends Craft {

  public LightObsidianCraft() {
    super("light_obsidian", new ItemStack(Material.SKULL_ITEM, 1));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
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
    return new ShapelessRecipe(getItem())
        .addIngredient(Material.WATER_BUCKET)
        .addIngredient(Material.LAVA_BUCKET);
  }

  @Override
  public String getName() {
    return "Light Obsidian";
  }
}
