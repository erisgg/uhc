package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.armorer.ArmorerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.duelist.DuelistVocationRegistry;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Vocation {

  DUELIST(DuelistVocationRegistry.get(), "Duelist", Material.IRON_SWORD),
  ARMORER(ArmorerVocationRegistry.get(), "Armorer", Material.IRON_CHESTPLATE),
  EXTRACTOR(null, "Extractor", Material.HOPPER),
  SPECIALIST(null, "Specialist", Material.EYE_OF_ENDER),
  HEALER(null, "Healer", Material.GOLDEN_APPLE),
  ENCHANTER(null, "Enchanter", Material.EXP_BOTTLE),
  SCIENTIST(null, "Scientist", Material.BLAZE_POWDER),
  MINER(null, "Miner", Material.IRON_PICKAXE);

  @Getter
  private final VocationRegistry registry;

  @Getter
  private final String display;

  @Getter
  private final ItemStack icon;

  Vocation(VocationRegistry registry, String display, Material icon) {
    this.registry = registry;
    this.display = display;
    this.icon = new ItemBuilder(icon).withName(CC.GREEN.bold() + display)
        .withLore(
            CC.GREEN.italic() + "Click to enter the " + display,
            CC.GREEN.italic() + "skill shop"
        ).build();
  }

  public static Unlockable getUnlockable(Identifier identifier) {
    if (identifier == null) {
      return null;
    }
    for (Vocation vocation : values()) {
      if (vocation.getRegistry().get(identifier) != null) {
        return vocation.getRegistry().get(identifier);
      }
    }

    return null;
  }

}
