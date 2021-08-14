package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class WarlordsLeggingsUltimate extends UltimateCraft {

  public WarlordsLeggingsUltimate() {
    super("warlords_leggings", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_LEGGINGS)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        )
        .nonTransformable()
        .color(CC.GREEN)
        .name("Warlord's Leggings")
        .quote("I wear the pants around here.")
        .quoteGiver("Ares")
        .effects("Gives the user strength and resistance 1 when worn.")
        .build());
  }

  @Override
  public String getName() {
    return "Warlord's Leggings";
  }


}
