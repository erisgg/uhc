package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public final class LightObsidianCraft extends Craft {

  public LightObsidianCraft() {
    super("light_obsidian", CraftableInfo.builder()
        .material(Material.OBSIDIAN)
        .actual(new ItemStack(Material.OBSIDIAN))
        .name("Light Obsidian")
        .color(CC.DARK_PURPLE)
        .quote("Wait, this makes no sense.")
        .quoteGiver("Scientist")
        .effects("Gives 1 obsidian")
        .build()
    );
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
    return new ShapelessRecipe(getDisplayItem())
        .addIngredient(Material.WATER_BUCKET)
        .addIngredient(Material.LAVA_BUCKET);
  }

  @Override
  public String getName() {
    return "Light Obsidian";
  }
}
