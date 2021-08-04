package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class DemigodSwordCraft extends Craft {

  public DemigodSwordCraft() {
    super("demigod_sword", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .build())
        .color(CC.AQUA)
        .name("Demigod Sword")
        .effects(
            "Sharpness I Iron Sword",
            "Sharpness II after 70 damage dealt",
            "Sharpness III after 160 damage dealt"
        )
        .quote("I can kill anything with this!")
        .quoteGiver("Forgotten Adventurer")
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
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
        .shape(" R ", "rsr", " r ")
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('s', Material.IRON_SWORD);
  }
}
