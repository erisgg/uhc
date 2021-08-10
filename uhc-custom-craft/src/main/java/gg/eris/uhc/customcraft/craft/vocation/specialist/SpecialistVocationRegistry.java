package gg.eris.uhc.customcraft.craft.vocation.specialist;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.specialist.craft.HardenedHelmetCraft;
import gg.eris.uhc.customcraft.craft.vocation.specialist.craft.LightAnvilCraft;
import gg.eris.uhc.customcraft.craft.vocation.specialist.craft.LightObsidianCraft;
import gg.eris.uhc.customcraft.craft.vocation.specialist.craft.SeekersCompassCraft;
import gg.eris.uhc.customcraft.craft.vocation.specialist.perk.SpecialistPerk;
import gg.eris.uhc.customcraft.craft.vocation.specialist.trinket.EirenesAllureTrinket;
import gg.eris.uhc.customcraft.craft.vocation.specialist.trinket.PrometheusProtectionTrinket;

public final class SpecialistVocationRegistry extends VocationRegistry {

  private static final SpecialistVocationRegistry REGISTRY = new SpecialistVocationRegistry();

  private final SpecialistPerk perk;

  private final EirenesAllureTrinket firstTrinket;
  private final PrometheusProtectionTrinket secondTrinket;

  private final LightObsidianCraft firstCraft;
  private final LightAnvilCraft secondCraft;
  private final HardenedHelmetCraft thirdCraft;
  private final SeekersCompassCraft fourthCraft;

  private SpecialistVocationRegistry() {
    this.perk = register(
        new SpecialistPerk(UhcPlugin.getPlugin().getCommons().getErisPlayerManager()));
    this.firstTrinket = register(new EirenesAllureTrinket());
    this.secondTrinket = register(new PrometheusProtectionTrinket());
    this.firstCraft = register(new LightObsidianCraft());
    this.secondCraft = register(new LightAnvilCraft());
    this.thirdCraft = register(new HardenedHelmetCraft());
    this.fourthCraft = register(new SeekersCompassCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "specialist";
  }

  @Override
  public SpecialistPerk getPerk() {
    return this.perk;
  }

  @Override
  public EirenesAllureTrinket getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public PrometheusProtectionTrinket getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public LightObsidianCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public LightAnvilCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public HardenedHelmetCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public SeekersCompassCraft getFourthCraft() {
    return this.fourthCraft;
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

  public static SpecialistVocationRegistry get() {
    return REGISTRY;
  }

}