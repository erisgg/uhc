package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class KellsUltimate extends UltimateCraft {

  public KellsUltimate() {
    super("kells", CraftableInfo.builder()
    .base(new ItemBuilder(Material.ENCHANTED_BOOK)
          .withEnchantment(Enchantment.DAMAGE_ALL, 3)
          .withEnchantment(Enchantment.FIRE_ASPECT, 1)
          .withEnchantment(Enchantment.ARROW_DAMAGE, 3)
          .withEnchantment(Enchantment.ARROW_FIRE, 1)
          .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
          .build()
    )
    .color(CC.LIGHT_PURPLE)
    .name("Kells")
    .quote("Enchant your sword, armor piece of bow with legendary effects!")
    .quoteGiver("Pandora")
        .effects("Gives the user an anvilable book that has the following enchantments on it:",
                  "-Sharpness 3",
                  "-Fire aspect 1",
                  "-Power 3",
                  "-Flame 1",
                  "-Protection 3")
        .build());
  }

  @Override
  public String getName() {
    return "Kells";
  }

  

}
