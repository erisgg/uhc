package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public final class ErosBowUltimate extends UltimateCraft {

  public ErosBowUltimate() {
    super("eros_bow", CraftableInfo.builder()
        .base(new ItemBuilder(Material.BOW)
            .withEnchantment(Enchantment.ARROW_DAMAGE, 3)
            .build()
        )
        .nonTransformable()
        .color(CC.DARK_RED)
        .name("Eros' Bow")
        .quote("Shoot your opponents from far range with Eros' bow!")
        .quoteGiver("Eros")
        .effects("Power 3 bow, with 25% chance of the arrow homing onto the enemy.",
            "Arrow will deal the damage of an unenchanted bowshot.")
        .build());
  }

  @Override
  public String getName() {
    return "Eros' Bow";
  }


}
