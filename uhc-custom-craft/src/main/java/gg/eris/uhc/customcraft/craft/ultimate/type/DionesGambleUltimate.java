package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;

public final class DionesGambleUltimate extends UltimateCraft {

  public DionesGambleUltimate() {
    super("diones_gamble", CraftableInfo.builder()
        .material(Material.ENDER_CHEST)
        .nonTransformable()
        .color(CC.RED)
        .name("Dione's Gamble")
        .quote("Risk your possibilities, to see if they will turn out in your favor!")
        .quoteGiver("Dione")
        .effects("Gives the user a completely random ultimate.")
        .build());
  }

  @Override
  public String getName() {
    return "Dione's Gamble";
  }


}
