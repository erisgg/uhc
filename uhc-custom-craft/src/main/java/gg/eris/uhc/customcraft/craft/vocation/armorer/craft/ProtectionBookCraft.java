package gg.eris.uhc.customcraft.craft.vocation.armorer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class ProtectionBookCraft extends Craft {

  public ProtectionBookCraft() {
    super("protection_book", CraftableInfo.builder()
        .base(new ItemBuilder(Material.ENCHANTED_BOOK)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
        ).nonCraftable()
        .color(CC.AQUA)
        .name("Protection Book")
        .quote("Know yourself!")
        .quoteGiver("Socrates")
        .effects("Protection 1 Book")
        .build()
    );
  }

  @Override
  public String getName() {
    return "Protection Book";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
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
            "pip",
            " p "
        ).setIngredient('l', Material.LEATHER)
        .setIngredient('p', Material.PAPER)
        .setIngredient('i', Material.IRON_INGOT);
  }

}
