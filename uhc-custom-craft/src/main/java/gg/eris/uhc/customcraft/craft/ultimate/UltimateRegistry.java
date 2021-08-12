package gg.eris.uhc.customcraft.craft.ultimate;

import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.customcraft.craft.ultimate.type.ExampleUltimate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public final class UltimateRegistry extends Registry<UltimateCraft> {

  private static final UltimateRegistry REGISTRY = new UltimateRegistry();

  @Getter
  private final List<UltimateCraft> ordered;

  private UltimateRegistry() {
    register(new ExampleUltimate());

    this.ordered = values().stream().sorted(Comparator.comparing(UltimateCraft::getName))
        .collect(Collectors.toUnmodifiableList());
  }

  public static UltimateRegistry get() {
    return REGISTRY;
  }

}
