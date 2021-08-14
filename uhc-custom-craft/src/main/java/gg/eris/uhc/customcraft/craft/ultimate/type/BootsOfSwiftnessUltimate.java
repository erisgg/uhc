package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class BootsOfSwiftnessUltimate extends UltimateCraft {

  public BootsOfSwiftnessUltimate() {
    super("boots_of_swiftness", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_BOOTS)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
            .withEnchantment(Enchantment.PROTECTION_FALL, 1)
            .withEnchantment(Enchantment.DURABILITY, 2)
            .build()
        )
        .nonTransformable()
        .color(CC.DARK_PURPLE)
        .name("Boots of Swiftness")
        .quote("Become faster than your enemies and speed past them!")
        .quoteGiver("Hermes")
        .effects(
            "Gives the user a pair of boots with protection 2, feather falling 1 and unbreaking 2, that will affect them with a 15% increase in walk speed.")
        .build());
  }

  @Override
  public String getName() {
    return "Boots of Swiftness";
  }


}
