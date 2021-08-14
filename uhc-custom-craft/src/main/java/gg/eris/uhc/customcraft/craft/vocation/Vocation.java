package gg.eris.uhc.customcraft.craft.vocation;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.vocation.armorer.ArmorerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.duelist.DuelistVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.EnchanterVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.extractor.ExtractorVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.healer.HealerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.miner.MinerVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.scientist.ScientistVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.specialist.SpecialistVocationRegistry;
import java.util.List;
import java.util.Locale;
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

  public String getStorageKey() {
    return name().toLowerCase(Locale.ROOT);
  }

  public VocationUnlockable getUnlockableFromMenuSlot(int slot) {
    return this.registry.getUnlockableFromMenuSlot(slot);
  }

  public static VocationUnlockable getUnlockable(Identifier identifier) {
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

  public static VocationUnlockable getUnlockable(ItemStack item) {
    if (StackUtil.isNullOrAir(item)) {
      return null;
    }

    String value = NBTUtil.getStringNbtData(item, CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY);

    if (value == null) {
      return null;
    }

    Identifier identifier = CustomCraftUhcIdentifiers.UNLOCKABLE.id(value);

    for (Vocation vocation : values()) {
      if (vocation.getRegistry().get(identifier) != null) {
        return vocation.getRegistry().get(identifier);
      }
    }

    return null;
  }

  public static boolean validateRegistries() {
    boolean valid = true;
    List<VocationUnlockable> unlockables = Lists.newArrayList();
    for (Vocation vocation : values()) {
      VocationRegistry registry = vocation.getRegistry();
      for (VocationUnlockable unlockable : registry.values()) {
        unlockables.add(unlockable);
        if (unlockable.getVocation() != registry.getVocation()) {
          valid = false;
          Bukkit.getLogger().warning(
              unlockable.getName() + " has invalid vocation (has " + unlockable.getVocation()
                  + " and requires " + registry.getVocation() + ").");
        }
      }
    }

    for (VocationUnlockable a : unlockables) {
      for (VocationUnlockable b : unlockables) {
        if (a != b && a.getName().equalsIgnoreCase(b.getName())) {
          valid = false;
          Bukkit.getLogger()
              .warning("Unlockable " + a.getName() + " has the same name as " + b.getName());
        }
      }
    }
    return valid;
  }

  public static Craftable getCraftable(int index) {
    int vocationIndex = Math.floorDiv(index, 6);
    if (vocationIndex >= values().length) {
      return null;
    }
    Vocation vocation = values()[vocationIndex];
    int item = index % 6;
    switch (item) {
      case 0:
        return vocation.getRegistry().getFirstCraft();
      case 1:
        return vocation.getRegistry().getSecondCraft();
      case 2:
        return vocation.getRegistry().getFirstTrinket();
      case 3:
        return vocation.getRegistry().getThirdCraft();
      case 4:
        return vocation.getRegistry().getFourthCraft();
      default:
        return vocation.getRegistry().getSecondTrinket();
    }

  }

}
