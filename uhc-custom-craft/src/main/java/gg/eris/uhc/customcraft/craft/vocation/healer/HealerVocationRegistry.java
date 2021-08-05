package gg.eris.uhc.customcraft.craft.vocation.healer;

import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.healer.craft.AppleEconomyCraft;
import gg.eris.uhc.customcraft.craft.vocation.healer.craft.GoldenHeadCraft;
import gg.eris.uhc.customcraft.craft.vocation.healer.craft.LightAppleCraft;
import gg.eris.uhc.customcraft.craft.vocation.healer.craft.SpeedSoupCraft;
import gg.eris.uhc.customcraft.craft.vocation.healer.perk.HealerPerk;
import gg.eris.uhc.customcraft.craft.vocation.healer.trinket.ForbiddenFruitTrinket;
import gg.eris.uhc.customcraft.craft.vocation.healer.trinket.KortexsGraceTrinket;

public final class HealerVocationRegistry extends VocationRegistry {

  private static final HealerVocationRegistry REGISTRY = new HealerVocationRegistry();

  private final HealerPerk perk;

  private final ForbiddenFruitTrinket firstTrinket;
  private final KortexsGraceTrinket secondTrinket;

  private final GoldenHeadCraft firstCraft;
  private final AppleEconomyCraft secondCraft;
  private final SpeedSoupCraft thirdCraft;
  private final LightAppleCraft fourthCraft;

  private HealerVocationRegistry() {
    this.perk = register(new HealerPerk());
    this.firstTrinket = register(new ForbiddenFruitTrinket());
    this.secondTrinket = register(new KortexsGraceTrinket());
    this.firstCraft = register(new GoldenHeadCraft());
    this.secondCraft = register(new AppleEconomyCraft());
    this.thirdCraft = register(new SpeedSoupCraft());
    this.fourthCraft = register(new LightAppleCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "healer";
  }

  @Override
  public HealerPerk getPerk() {
    return this.perk;
  }

  @Override
  public ForbiddenFruitTrinket getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public KortexsGraceTrinket getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public GoldenHeadCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public AppleEconomyCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public SpeedSoupCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public LightAppleCraft getFourthCraft() {
    return this.fourthCraft;
  }

  public static HealerVocationRegistry get() {
    return REGISTRY;
  }

}
