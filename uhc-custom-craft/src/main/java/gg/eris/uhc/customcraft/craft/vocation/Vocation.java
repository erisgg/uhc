package gg.eris.uhc.customcraft.craft.vocation;

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

}
