package gg.eris.uhc.customcraft.craft.vocation.armorer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DemigodHelmet extends Craft {

  public DemigodHelmet() {
    super("demigod_helmet", CraftableInfo.builder()
        .nonTransformable()
        .base(new ItemBuilder(Material.IRON_HELMET)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
            .withEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
            .withEnchantment(Enchantment.PROTECTION_FIRE, 1)
            .build()
        )
        .color(CC.AQUA)
        .name("Demigod Helmet")
        .quote("Eureka!")
        .quoteGiver("Archimedes")
        .effects("Protection 1, Fire Protection 1, Projectile Protection 1, Unbreaking 1 Iron "
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
    return new ShapedRecipe(getItem())
        .shape(
            " R ",
            "rhr",
            " r "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('h', Material.IRON_HELMET);
  }

  @Override
  public String getName() {
    return "Demigod Helmet";
  }
}
