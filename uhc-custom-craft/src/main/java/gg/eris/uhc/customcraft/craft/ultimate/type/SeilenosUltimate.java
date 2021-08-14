package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class SeilenosUltimate extends UltimateCraft {

  public SeilenosUltimate() {
    super("seilenos", CraftableInfo.builder()
        .base(new ItemStack(Material.POTION, 1, (byte) 0))
        .nonTransformable()
        .color(CC.DARK_BLUE)
        .name("Seilenos")
        .quote("A drink left behind from an ancient Roman party.")
        .quoteGiver("Circe")
        .effects("Gives the user slowness 1 and strength 1 for 15 seconds")
        .build());
  }

  @Override
  public String getName() {
    return "Seilenos";
  }


}
