package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class TrousersOfTheLochnessUltimate extends UltimateCraft {

  public TrousersOfTheLochnessUltimate() {
    super("trousers_of_the_lochness", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_LEGGINGS)
            .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
            .withEnchantment(Enchantment.OXYGEN, 2)
            .withEnchantment(Enchantment.DURABILITY, 2)
            .build()
        )
        .nonTransformable()
        .color(CC.BLUE)
        .name("Trousers of the Lochness")
        .quote("The Lochness left some trousers behind")
        .quoteGiver("The Lochness Monster")
        .effects("Give the user diamond leggings with the enchantments"
            + "-Protection 4"
            + "-Respiration 2"
            + "-Unbreaking 2")
        .build());
  }

  @Override
  public String getName() {
    return "Trouser of the Lochness";
  }


}
