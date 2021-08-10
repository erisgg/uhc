package gg.eris.uhc.customcraft.craft.vocation;

import com.google.common.collect.Lists;
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
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
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

  public static boolean validateRegistries() {
    boolean valid = true;
    List<Unlockable> unlockables = Lists.newArrayList();
    for (Vocation vocation : values()) {
      VocationRegistry registry = vocation.getRegistry();
      for (Unlockable unlockable : registry.getUnlockables()) {
        unlockables.add(unlockable);
        if (unlockable.getVocation() != registry.getVocation()) {
          valid = false;
          Bukkit.getLogger().warning(unlockable.getName() + " has invalid vocation (has " + unlockable.getVocation() + " and requires " + registry.getVocation() + ").");
        }
      }
    }

    for (Unlockable a : unlockables) {
      for (Unlockable b : unlockables) {
        if (a != b && a.getName().equalsIgnoreCase(b.getName())) {
          valid = false;
          Bukkit.getLogger().warning("Unlockable " + a.getName() + " has the same name as " + b.getName());
        }
      }
    }
    return valid;
  }

}
