package gg.eris.uhc.customcraft.craft.ultimate.type;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.ultimate.UltimateCraft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ExampleUltimate extends UltimateCraft {

  public ExampleUltimate() {
    super("example", CraftableInfo.builder()
    .material(Material.APPLE)
    .nonTransformable()
    .color(CC.RED)
    .name("Example")
    .quote("quote")
    .quoteGiver("giver")
        .effects("stuff")
        .build());
  }

  @Override
  public String getName() {
    return "test";
  }
}
