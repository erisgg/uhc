package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class AtlasEyeDropsUltimate extends UltimateCraft {

  public AtlasEyeDropsUltimate() {
    super("atlas_eye_drops", CraftableInfo.builder()
        .base(new ItemStack(Material.POTION, 1, (byte) 0))
        .color(CC.AQUA)
        .nonTransformable()
        .name("Atlas' Eye Drops")
        .quote("I forgot to pick up my meds!")
        .quoteGiver("Atlas")
        .effects("Gives resistance 2 for 15 seconds.")
        .build());
  }

  @Override
  public String getName() {
    return "Atlas' Eye Drops";
  }


}
