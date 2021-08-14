package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;

public final class KamaOfDeathUltimate extends UltimateCraft {

  public KamaOfDeathUltimate() {
    super("kama_of_death", CraftableInfo.builder()
        .material(Material.DIAMOND_HOE)
        .nonTransformable()
        .color(CC.DARK_RED)
        .name("Kama of Death")
        .quote("Steal health from your enemies to get stronger!")
        .quoteGiver("Prometheus")
        .effects(
            "Deals 20% of your opponents current health on hit, and heals yourself for 25% of the damage dealt! (15 usages)")
        .build());
  }

  @Override
  public String getName() {
    return "Kama of Death";
  }


}
