package gg.eris.uhc.customcraft.craft.vocation.specialist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class PrometheusProtectionTrinket extends Trinket {

  public PrometheusProtectionTrinket() {
    super("prometheus_protection", CraftableInfo.builder()
        .material(Material.BLAZE_POWDER)
        .name("Prometheus' Protection")
        .color(CC.DARK_RED)
        .quote("That's hot!")
        .quoteGiver("Will Smith")
        .effects(
            "Take 50% less damage from fire"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "gfg",
            "glg",
            "ggg"
        ).setIngredient('g', new MaterialData(Material.STAINED_GLASS_PANE, (byte) DataUtil.ORANGE))
        .setIngredient('f', Material.FLINT_AND_STEEL)
        .setIngredient('l', Material.LAVA_BUCKET);
  }

  @Override
  public String getName() {
    return "Prometheus' Protection";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }
}
