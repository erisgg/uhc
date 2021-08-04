package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class DemigodBowCraft extends Craft {

  public DemigodBowCraft() {
    super("demigod_bow", CraftableInfo.builder()
        .base(new ItemBuilder(Material.BOW).withEnchantment(Enchantment.ARROW_DAMAGE, 1).build())
        .name("Demigod Bow")
        .quote("And you have my bow.")
        .quoteGiver("Legolas")
        .effects(
            "Power I Bow",
            "Power II 15 minutes after PvP",
            "Power III during deathmatch"
        )
        .color(CC.AQUA)
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
        .shape(
            " R ",
            "rbr",
            " r "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('b', Material.BOW);
  }
}