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


public class DemigodBootsCraft extends Craft {

  public DemigodBootsCraft() {
    super("demigod_boots", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_BOOTS)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
            .withEnchantment(Enchantment.PROTECTION_FIRE, 1)
            .withEnchantment(Enchantment.PROTECTION_PROJECTILE, 1)
            .build()
        ).nonTransformable()
        .color(CC.AQUA)
        .name("Demigod Boots")
        .quote("Hippopotamus")
        .quoteGiver("Hippocrates")
        .effects(
            "Protection 1, Fire Protection 1, Projectile Protection 1, Unbreaking 1 Iron Boots")
        .build()
    );
  }

  @Override
  public String getName() {
    return "Demigod Boots";
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
            "rbr",
            " r "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('b', CraftHelper.durabilityIgnored(Material.IRON_BOOTS));
  }
}
