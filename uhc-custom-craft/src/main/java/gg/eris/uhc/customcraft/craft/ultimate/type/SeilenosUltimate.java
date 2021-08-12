package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class SeilenosUltimate extends UltimateCraft {

  public SeilenosUltimate() {
    super("seilenos", CraftableInfo.builder()
    .base(new ItemStack(Material.GLASS_BOTTLE, 1, (byte)0))
    .nonTransformable()
    .color(CC.DARK_BLUE)
    .name("Seilenos")
    .quote("A drink left behind from an ancient Roman party.")
    .quoteGiver("Circe")
        .effects("Give the user slowness 1 and strength 1 for 15 seconds.")
        .build());
  }

  @Override
  public String getName() {
    return "Seilenos";
  }

  

}
