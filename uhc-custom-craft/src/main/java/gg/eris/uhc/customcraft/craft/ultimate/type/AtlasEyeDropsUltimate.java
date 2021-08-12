package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class AtlasEyeDropsUltimate extends UltimateCraft {

  public AtlasEyeDropsUltimate() {
    super("atlas_eye_drops", CraftableInfo.builder()
    .base(new ItemStack(Material.GLASS_BOTTLE, 1, (byte)0))
    .color(CC.AQUA)
    .nonTransformable()
    .name("Atlas' Eye Drops")
    .quote("Someone forgot to pick up their meds.")
    .quoteGiver("Atlas")
        .effects("Gives the user resistance 2 for 15 seconds.")
        .build());
  }

  @Override
  public String getName() {
    return "Atlas' Eye Drops";
  }

  

}
