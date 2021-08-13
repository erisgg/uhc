package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public final class InstantDamagePotionCraft extends Craft {

  public InstantDamagePotionCraft() {
    super("instant_damage_potion",
        new ItemBuilder(new Potion(PotionType.INSTANT_DAMAGE, 2, true, false).toItemStack(1))
    .withName(CC.WHITE + "Instant Damage II").build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
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
        .shape(" b ", "dhd", "obo")
        .setIngredient('b', Material.BONE)
        .setIngredient('d', new MaterialData(Material.INK_SACK, DataUtil.GREEN_DYE))
        .setIngredient('h', Material.SKULL_ITEM)
        .setIngredient('b', Material.GLASS_BOTTLE);
  }

  @Override
  public String getName() {
    return "Instant Damage II Potion";
  }
}
