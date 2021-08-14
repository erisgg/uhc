package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LightAnvilCraft extends Craft {

  public LightAnvilCraft() {
    super("light_anvil", CraftableInfo.builder()
        .color(CC.YELLOW)
        .name("Light Anvil")
        .quote("Why isn't the world made of these?")
        .quoteGiver("Atlas")
        .actual(new ItemStack(Material.ANVIL))
        .material(Material.ANVIL)
        .effects("Gives an anvil")
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
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
        .shape("iii", " I ", "iii")
        .setIngredient('i', Material.IRON_INGOT)
        .setIngredient('I', Material.IRON_BLOCK);
  }

  @Override
  public String getName() {
    return "Light Anvil";
  }
}
