package gg.eris.uhc.customcraft.craft.vocation.extractor.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class PowerBookCraft extends Craft {

  public PowerBookCraft() {
    super("power_book", CraftableInfo.builder()
        .color(CC.LIGHT_PURPLE)
        .base(new ItemBuilder(Material.ENCHANTED_BOOK)
            .withEnchantment(Enchantment.ARROW_DAMAGE, 1)
            .nonCraftable()
            .nonBrewable()
            .build())
        .actual(new ItemBuilder(Material.ENCHANTED_BOOK)
            .withEnchantment(Enchantment.ARROW_DAMAGE, 1)
            .nonCraftable()
            .nonBrewable()
            .build())
        .name("Power I Book")
        .quote("I AM POWERFUL!")
        .quoteGiver("Someone who isn't powerful")
        .effects("Gives a Power I Book")
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
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
    return new ShapedRecipe(getActualItem())
        .shape(
            " l ",
            "pap",
            " p "
        ).setIngredient('a', Material.ARROW)
        .setIngredient('p', Material.PAPER)
        .setIngredient('l', Material.LEATHER);
  }

  @Override
  public String getName() {
    return "Power I Book";
  }

}
