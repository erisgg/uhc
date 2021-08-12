package gg.eris.uhc.customcraft.craft.vocation.enchanter.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class AresSiphon extends Trinket {

  public AresSiphon() {
    super("ares_siphon", CraftableInfo.builder()
        .material(Material.ACTIVATOR_RAIL)
        .name("Ares' Siphon")
        .color(CC.RED)
        .quote("Shut up.")
        .quoteGiver("Ares")
        .effects(
            "Get 50% more experience when you kill a player"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "rgr",
            "gLg",
            "rgr"
        ).setIngredient('r', Material.REDSTONE)
        .setIngredient('g', Material.GLOWSTONE_DUST)
        .setIngredient('L', Material.LAPIS_BLOCK);
  }

  @Override
  public String getName() {
    return "Ares' Siphon";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }
}
