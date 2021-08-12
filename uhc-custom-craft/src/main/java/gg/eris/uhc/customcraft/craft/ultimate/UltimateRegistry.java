package gg.eris.uhc.customcraft.craft.ultimate;

import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.customcraft.craft.ultimate.type.AergiasSnowballUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.AtlasEyeDropsUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.BarkemakUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.BootsOfSwiftnessUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.DionesGambleUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.DurendalUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.ErosBowUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.ExampleUltimate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import gg.eris.uhc.customcraft.craft.ultimate.type.KamaOfDeathUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.KellsUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.ProtectorsChestplateUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.ReapersCleaverUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.SeilenosUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.TrousersOfTheLochnessUltimate;
import gg.eris.uhc.customcraft.craft.ultimate.type.WarlordsLeggingsUltimate;
import lombok.Getter;

public final class UltimateRegistry extends Registry<UltimateCraft> {

  private static final UltimateRegistry REGISTRY = new UltimateRegistry();

  @Getter
  private final List<UltimateCraft> ordered;

  private UltimateRegistry() {
    register(new AergiasSnowballUltimate());
    register(new AtlasEyeDropsUltimate());
    register(new BarkemakUltimate());
    register(new BootsOfSwiftnessUltimate());
    register(new DionesGambleUltimate());
    register(new DurendalUltimate());
    register(new ErosBowUltimate());
    register(new KamaOfDeathUltimate());
    register(new KellsUltimate());
    register(new ProtectorsChestplateUltimate());
    register(new ReapersCleaverUltimate());
    register(new SeilenosUltimate());
    register(new TrousersOfTheLochnessUltimate());
    register(new WarlordsLeggingsUltimate());

    this.ordered = values().stream().sorted(Comparator.comparing(UltimateCraft::getName))
        .collect(Collectors.toUnmodifiableList());
  }

  public static UltimateRegistry get() {
    return REGISTRY;
  }

}
