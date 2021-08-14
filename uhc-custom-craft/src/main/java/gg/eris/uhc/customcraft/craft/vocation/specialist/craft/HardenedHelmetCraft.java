package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HardenedHelmetCraft extends Craft {

  public HardenedHelmetCraft() {
    super("hardened_helmet", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_HELMET)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
            .withEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
            .withEnchantment(Enchantment.PROTECTION_FIRE, 1)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).color(CC.DARK_GREEN)
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
