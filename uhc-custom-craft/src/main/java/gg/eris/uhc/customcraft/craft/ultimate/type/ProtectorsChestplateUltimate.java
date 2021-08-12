package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class ProtectorsChestplateUltimate extends UltimateCraft {

  public ProtectorsChestplateUltimate() {
    super("protectors_chestplate", CraftableInfo.builder()
    .base(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
          .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
          .withEnchantment(Enchantment.DURABILITY, 1)
          .build()
    )
    .color(CC.LIGHT_PURPLE)
    .nonTransformable()
    .name("Protector's Chestplate")
    .quote("Armor yourself up with the Protector's Chestplate!")
    .quoteGiver("Soteria")
        .effects("Gives the user a diamond chestplate that has the following enchantments on it:"
                  + "-Protection 4"
                  + "-Unbreaking 1")
        .build());
  }

  @Override
  public String getName() {
    return "Protector's Chestplate";
  }

  

}
