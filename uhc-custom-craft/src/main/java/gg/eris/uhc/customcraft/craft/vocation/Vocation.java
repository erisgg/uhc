package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.armorer.ArmorerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.duelist.DuelistVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.EnchanterVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.extractor.ExtractorVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.healer.HealerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.miner.MinerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.scientist.ScientistVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.specialist.SpecialistVocationRegistry;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Vocation {

  DUELIST(DuelistVocationRegistry.get(), "Duelist", Material.IRON_SWORD),
  ARMORER(ArmorerVocationRegistry.get(), "Armorer", Material.IRON_CHESTPLATE),
  EXTRACTOR(ExtractorVocationRegistry.get(), "Extractor", Material.HOPPER),
  SPECIALIST(SpecialistVocationRegistry.get(), "Specialist", Material.EYE_OF_ENDER),
  HEALER(HealerVocationRegistry.get(), "Healer", Material.GOLDEN_APPLE),
  ENCHANTER(EnchanterVocationRegistry.get(), "Enchanter", Material.EXP_BOTTLE),
  SCIENTIST(ScientistVocationRegistry.get(), "Scientist", Material.BLAZE_POWDER),
  MINER(MinerVocationRegistry.get(), "Miner", Material.IRON_PICKAXE);

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
