package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HardenedHelmetCraft extends Craft {

  public HardenedHelmetCraft() {
    super("hardened_helmet", CraftableInfo.builder()
        .material(Material.DIAMOND_HELMET)
        .color(CC.DARK_GREEN)
        .name("Hardened Helmet")
        .quote("It belongs on your head!")
        .quoteGiver("Hardened Helmet Manual")
        .effects(("Protection I, Projectile Protection I, Fire Protection I, Unbreaking I Diamond "
            + "Helmet"))
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
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
            "dLd",
            "did",
            "   "
        ).setIngredient('d', Material.DIAMOND)
        .setIngredient('i', Material.IRON_INGOT)
        .setIngredient('L', Material.LAPIS_BLOCK);
  }

  @Override
  public String getName() {
    return "Hardened Helmet";
  }
}
