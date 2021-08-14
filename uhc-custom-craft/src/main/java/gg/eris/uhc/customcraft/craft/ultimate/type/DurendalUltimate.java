package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class DurendalUltimate extends UltimateCraft {

  public DurendalUltimate() {
    super("durendal", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 2)
            .build()
        )
        .nonTransformable()
        .color(CC.DARK_AQUA)
        .name("Durendal")
        .quote("Increase your speed and become swift like the wind!")
        .quoteGiver("Hermes")
        .effects(
            "Gives the user an iron sword with sharpness 2, that affects them with speed 1 and resistance 1.")
        .build());
  }

  @Override
  public String getName() {
    return "Durendal";
  }


}
