package gg.eris.uhc.customcraft.craft;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

@UtilityClass
public class CraftHelper {

  public static MaterialData durabilityIgnored(Material material) {
    return new MaterialData(material, (byte) -1);
  }

}
