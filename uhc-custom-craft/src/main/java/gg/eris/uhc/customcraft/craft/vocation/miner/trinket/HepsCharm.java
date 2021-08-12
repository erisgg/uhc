package gg.eris.uhc.customcraft.craft.vocation.miner.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HepsCharm extends Trinket {

  public HepsCharm() {
    super("heps_charm", CraftableInfo.builder()
        .material(Material.ACTIVATOR_RAIL)
        .name("Hep's Charm")
        .color(CC.GOLD)
        .quote("Can we fix it?")
        .quoteGiver("Bob, a builder")
        .effects(
            "Tools take 25% less damage"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape("iri",
            "ror",
            "iri").setIngredient('i', Material.IRON_INGOT)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('o', Material.OBSIDIAN);
  }

  @Override
  public String getName() {
    return "Hep's Charm";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }
}
