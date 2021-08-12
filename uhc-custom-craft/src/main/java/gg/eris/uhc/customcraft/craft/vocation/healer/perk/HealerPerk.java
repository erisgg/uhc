package gg.eris.uhc.customcraft.craft.vocation.healer.perk;

import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;

/**
 * Gives 3% exp extra per level
 */
public final class HealerPerk extends Perk {


  public HealerPerk() {
    super("healer_perk");
  }

  @Override
  public String getName() {
    return "Healer Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables(
        "Gives Speed II for {0} seconds when consuming a golden head",
        5 + (level - 1));
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

}
