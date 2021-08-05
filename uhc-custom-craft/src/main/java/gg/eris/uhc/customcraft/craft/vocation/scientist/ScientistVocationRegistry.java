package gg.eris.uhc.customcraft.craft.vocation.scientist;

import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.scientist.craft.AbsorberCraft;
import gg.eris.uhc.customcraft.craft.vocation.scientist.craft.GlowstoneCraft;
import gg.eris.uhc.customcraft.craft.vocation.scientist.craft.InstantDamagePotionCraft;
import gg.eris.uhc.customcraft.craft.vocation.scientist.craft.NetherWartCraft;
import gg.eris.uhc.customcraft.craft.vocation.scientist.perk.ScientistPerk;
import gg.eris.uhc.customcraft.craft.vocation.scientist.trinket.ChronosClock;
import gg.eris.uhc.customcraft.craft.vocation.scientist.trinket.HadesHellPowder;

public final class ScientistVocationRegistry extends VocationRegistry {

  private static final ScientistVocationRegistry REGISTRY = new ScientistVocationRegistry();

  private final ScientistPerk perk;

  private final HadesHellPowder firstTrinket;
  private final ChronosClock secondTrinket;

  private final NetherWartCraft firstCraft;
  private final GlowstoneCraft secondCraft;
  private final AbsorberCraft thirdCraft;
  private final InstantDamagePotionCraft fourthCraft;

  private ScientistVocationRegistry() {
    this.perk = register(new ScientistPerk());
    this.firstTrinket = register(new HadesHellPowder());
    this.secondTrinket = register(new ChronosClock());
    this.firstCraft = register(new NetherWartCraft());
    this.secondCraft = register(new GlowstoneCraft());
    this.thirdCraft = register(new AbsorberCraft());
    this.fourthCraft = register(new InstantDamagePotionCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "scientist";
  }

  @Override
  public ScientistPerk getPerk() {
    return this.perk;
  }

  @Override
  public HadesHellPowder getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public ChronosClock getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public NetherWartCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public GlowstoneCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public AbsorberCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public InstantDamagePotionCraft getFourthCraft() {
    return this.fourthCraft;
  }

  public static ScientistVocationRegistry get() {
    return REGISTRY;
  }

}
