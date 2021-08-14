package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class ReapersCleaverUltimate extends UltimateCraft {

  public ReapersCleaverUltimate() {
    super("reapers_cleaver", CraftableInfo.builder()
    .material(Material.IRON_AXE)
    .nonTransformable()
    .color(CC.WHITE)
    .name("Reaper's Cleaver")
    .quote("Your time has come")
    .quoteGiver("The Grim Reaper")
        .effects("Gives the victim wither 1 for 10 seconds (10 usages)")
        .build());
  }

  @Override
  public String getName() {
    return "Reaper's Cleaver";
  }

}
