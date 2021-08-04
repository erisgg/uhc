package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.CC;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Builder
@Value
public class TrinketInfo {

  Material material;
  String name;
  String quote;
  String quoteGiver;
  String effect;
  CC color;


}
