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

public final class CompactBowCraft extends Craft {

  public CompactBowCraft() {
    super("compact_bow", CraftableInfo.builder()
        .base(new ItemBuilder(Material.BOW).withEnchantment(Enchantment.ARROW_DAMAGE, 1).build())
        .name("Compact Bow")
        .color(CC.DARK_PURPLE)
        .quote("It's so compact!")
        .quoteGiver("Billy Mays")
        .effects("Power I Bow")
        .nonTransformable()
        .build());
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
    return new ShapedRecipe(getItem())
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
    return "Compact Bow";
  }

}
