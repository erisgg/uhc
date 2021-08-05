package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ModularWand extends Craft {

  public ModularWand() {
    super("modular_wand", CraftableInfo.builder()
        .material(Material.BLAZE_ROD)
        .color(CC.RED)
        .name("Modular Wand")
        .quote("Expelliarmus!")
        .quoteGiver("Harry Potter")
        .effects(
            "Lightning mode: Hit an opponent to strike them with lightning",
            "Fire mode: Hit an opponent to set them on fire",
            "Right click to swap",
            "10s cooldown"
        )
        .build());
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
        .shape(
            " t ",
            " s ",
            "pbp"
        )
        .setIngredient('t', Material.TNT)
        .setIngredient('s', Material.SKULL_ITEM)
        .setIngredient('p', Material.BLAZE_POWDER)
        .setIngredient('b', Material.BLAZE_ROD);
  }

  @Override
  public String getName() {
    return "Modular Wand";
  }
}
