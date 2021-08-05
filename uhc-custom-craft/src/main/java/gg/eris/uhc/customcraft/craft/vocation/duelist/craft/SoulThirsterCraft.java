package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class SoulThirsterCraft extends Craft {

  public SoulThirsterCraft() {
    super("soul_thirster", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .build())
        .color(CC.DARK_RED)
        .name("Soul Thirster")
        .quote("I want to suck your blood!")
        .quoteGiver("Dracula")
        .effects(
            "Sharpness I Diamond Sword",
            "Increases by 1 sharpness level each kill, up to Sharpness IV",
            "Decays a sharpness level every 5 minutes without a kill, down to Sharpness I"
        ).nonTransformable()
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
            "xdx",
            "lsl",
            "xLx"
        ).setIngredient('x', Material.EXP_BOTTLE)
        .setIngredient('d', Material.DIAMOND)
        .setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('L', Material.LAPIS_BLOCK)
        .setIngredient('s', Material.DIAMOND_SWORD);
  }

  @Override
  public String getName() {
    return "Soul Thirster";
  }

}
