package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public final class InstantDamagePotionCraft extends Craft {

  public InstantDamagePotionCraft() {
    super("instant_damage_potion", CraftableInfo.builder()
        .color(CC.DARK_RED)
        .name("Instant Damage II Splash Potion")
        .quote("One splash of this will do the trick.")
        .quoteGiver("Back-Alley Dealer")
        .base(new ItemBuilder(new Potion(PotionType.INSTANT_DAMAGE, 2, true, false).toItemStack(1))
            .withName(CC.WHITE + "Instant Damage II Splash Potion").build())
        .actual(new ItemBuilder(new Potion(PotionType.INSTANT_DAMAGE, 2, true, false).toItemStack(1))
            .withName(CC.WHITE + "Instant Damage II Splash Potion").build())
        .effects("Gives 1 Instant Damage II splash potion")
        .build()
    );
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
    return new ShapedRecipe(getActualItem())
        .shape(" b ", "dhd", "oBo")
        .setIngredient('b', Material.BONE)
        .setIngredient('d', new MaterialData(Material.INK_SACK, DataUtil.GREEN_DYE))
        .setIngredient('h', new MaterialData(Material.SKULL_ITEM,
            (byte) SkullType.PLAYER.ordinal()))
        .setIngredient('B', new MaterialData(Material.POTION, (byte) 0))
        .setIngredient('o', Material.OBSIDIAN);
  }

  @Override
  public String getName() {
    return "Instant Damage II Potion";
  }
}
