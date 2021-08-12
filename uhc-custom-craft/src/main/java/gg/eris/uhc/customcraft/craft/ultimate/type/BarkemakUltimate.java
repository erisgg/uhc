package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;

public final class BarkemakUltimate extends UltimateCraft {

  public BarkemakUltimate() {
    super("diones_gamble", CraftableInfo.builder()
    .material(Material.DIAMOND_HELMET)
    .nonTransformable()
    .color(CC.GOLD)
    .name("Barkemak")
    .quote("Heal your wounds with Barkemak’s magic!")
    .quoteGiver("Asclepius")
        .effects("Heals the beholder for half a heart, when the user connects any form of hit from a sword or bow onto an enemy. (1 second cooldown)")
        .build());
  }

  @Override
  public String getName() {
    return "Barkemak";
  }

  

}
