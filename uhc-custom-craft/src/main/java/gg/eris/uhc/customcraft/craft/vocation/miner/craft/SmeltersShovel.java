package gg.eris.uhc.customcraft.craft.vocation.miner.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SmeltersShovel extends Craft {

  public SmeltersShovel() {
    super("smelters_shovel", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_PICKAXE).withEnchantment(Enchantment.DIG_SPEED, 1).build())
        .color(CC.GRAY)
        .name("Smelter's Shovel")
        .quote("Diggy diggy dirt!")
        .quoteGiver("Dirtscast")
        .effects("Efficiency 1 Iron Shovel", "Automatically smelts sand into glass", "100% chance"
            + " of receiving flint from gravel")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
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
        .shape("ici", " s ", "   ")
        .setIngredient('i', Material.IRON_ORE)
        .setIngredient('c', Material.COAL)
        .setIngredient('s', Material.STONE_SPADE);
  }

  @Override
  public String getName() {
    return "Smelters's Shovel";
  }
}
