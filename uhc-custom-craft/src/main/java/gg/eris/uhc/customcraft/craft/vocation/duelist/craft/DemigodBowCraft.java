package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class DemigodBowCraft extends Craft {

  public DemigodBowCraft() {
    super("demigod_bow", CraftableInfo.builder()
        .base(new ItemBuilder(Material.BOW)
            .withEnchantment(Enchantment.ARROW_DAMAGE, 1)
            .build()
        )
        .color(CC.AQUA)
        .name("Demigod Bow")
        .quote("You have my bow.")
        .quoteGiver("Legolas")
        .effects(
            "Power I Bow",
            "Power II 15 minutes after PvP",
            "Power III during deathmatch"
        )
        .nonTransformable()
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

  @Override
  public String getName() {
    return "Demigod Bow";
  }

}
