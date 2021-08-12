package gg.eris.uhc.customcraft.craft.vocation.miner.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class PlutusFoot extends Trinket {

  public PlutusFoot() {
    super("plutus_foot", CraftableInfo.builder()
        .material(Material.RABBIT_FOOT)
        .name("Plutus' Foot")
        .color(CC.GOLD)
        .quote("What's the price?")
        .quoteGiver("Plutus, with 2 feet")
        .effects(
            "Gives the player Haste I permanently",
            "Haste bonuses become Haste II"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "sss",
            "fpf",
            "ccc")
        .setIngredient('s', Material.SUGAR)
        .setIngredient('c', Material.SUGAR_CANE)
        .setIngredient('f', Material.FEATHER)
        .setIngredient('p', Material.IRON_PICKAXE);
  }

  @Override
  public String getName() {
    return "Plutus' Foot";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }
}
