package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

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
import org.bukkit.material.MaterialData;

public final class SharpnessBookCraft extends Craft {

  public SharpnessBookCraft() {
    super("sharpness_book", CraftableInfo.builder()
        .name("Sharpness I Book")
        .effects("Gives a Sharpness I Book")
        .color(CC.DARK_GREEN)
        .quote("Ow, a papercut.")
        .quoteGiver("Ares")
        .actual(
            new ItemBuilder(Material.ENCHANTED_BOOK)
                .withEnchantment(Enchantment.DAMAGE_ALL, 1)
                .nonCraftable()
                .nonBrewable()
                .build()
        ).base(
            new ItemBuilder(Material.ENCHANTED_BOOK)
                .withEnchantment(Enchantment.DAMAGE_ALL, 1)
                .nonCraftable()
                .nonBrewable()
                .build()
        ).build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
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
            "psp",
            " p "
        ).setIngredient('l', Material.LEATHER)
        .setIngredient('p', Material.PAPER)
        .setIngredient('s', CraftHelper.durabilityIgnored(Material.IRON_SWORD));
  }

  @Override
  public String getName() {
    return "Sharpness I Book";
  }

}
