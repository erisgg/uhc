package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;

public final class AergiasSnowballUltimate extends UltimateCraft {

  public AergiasSnowballUltimate() {
    super("aergias_snowball", CraftableInfo.builder()
        .material(Material.SNOW_BALL)
        .nonTransformable()
        .color(CC.WHITE)
        .name("Aergia's Snowball")
        .quote("Anyone to come across this snowball will feel Aergia's Fatigue")
        .quoteGiver("Aergia")
        .effects("Slowness 1 for 5 seconds, 25 second cooldown")
        .build());
  }

  @Override
  public String getName() {
    return "Aergia's Snowball";
  }


}
