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

public final class LuckPick extends Craft {

  public LuckPick() {
    super("luck_pick", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
            .withDurability((short) 2)
            .withEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2)
            .build()
        )
        .color(CC.YELLOW)
        .name("Luck Pick")
        .quote("This is the LuckPickingLawyer")
        .quoteGiver("LuckPickingLawyer")
        .effects("Nearly broken Fortune 2 Diamond Pickaxe")
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
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
            "gIg",
            "lsl",
            " s "
        )
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('l', Material.LAPIS_BLOCK)
        .setIngredient('s', Material.STICK)
        .setIngredient('I', Material.IRON_BLOCK);
  }

  @Override
  public String getName() {
    return "Luck Pick";
  }
}