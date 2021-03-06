package gg.eris.uhc.customcraft.craft.vocation.armorer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.CraftHelper;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DemigodHelmetCraft extends Craft {

  public DemigodHelmetCraft() {
    super("demigod_helmet", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_HELMET)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
            .withEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
            .withEnchantment(Enchantment.PROTECTION_FIRE, 1)
            .build()
        ).nonTransformable()
        .color(CC.AQUA)
        .name("Demigod Helmet")
        .quote("Eureka!")
        .quoteGiver("Archimedes")
        .effects("Protection I, Fire Protection I, Projectile Protection I, Unbreaking I Iron "
            + "Helmet")
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
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
    return new ShapedRecipe(getActualItem())
        .shape(
            " R ",
            "rhr",
            " r "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('h', CraftHelper.durabilityIgnored(Material.IRON_HELMET));
  }

  @Override
  public String getName() {
    return "Demigod Helmet";
  }
}
