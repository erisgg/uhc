package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class LightAnvilCraft extends Craft {

  public LightAnvilCraft() {
    super("light_anvil", new ItemStack(Material.ANVIL));
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
    return new ShapedRecipe(getItem())
        .shape("iii", " I ", "iii")
        .setIngredient('i', Material.IRON_INGOT)
        .setIngredient('I', Material.IRON_BLOCK);
  }

  @Override
  public String getName() {
    return "Light Anvil";
  }
}
