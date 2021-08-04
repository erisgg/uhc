package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.duelist.DuelistVocationRegistry;
import lombok.Getter;

public enum Vocation {

  DUELIST(DuelistVocationRegistry.get()),
  ARMORER(null),
  EXTRACTOR(null),
  SPECIALIST(null),
  HEALER(null),
  ENCHANTER(null),
  SCIENTIST(null),
  MINER(null);

  @Getter
  private final VocationRegistry registry;

  Vocation(VocationRegistry registry) {
    this.registry = registry;
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
