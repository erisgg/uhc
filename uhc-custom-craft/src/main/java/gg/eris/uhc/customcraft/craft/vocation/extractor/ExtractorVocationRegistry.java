package gg.eris.uhc.customcraft.craft.vocation.extractor;

import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.extractor.craft.ArrowEconomyCraft;
import gg.eris.uhc.customcraft.craft.vocation.extractor.craft.CompactBowCraft;
import gg.eris.uhc.customcraft.craft.vocation.extractor.craft.SaddleCraft;
import gg.eris.uhc.customcraft.craft.vocation.extractor.craft.SugarEconomyCraft;
import gg.eris.uhc.customcraft.craft.vocation.extractor.perk.ExtractorPerk;
import gg.eris.uhc.customcraft.craft.vocation.extractor.trinket.ArtemisQuiverTrinket;
import gg.eris.uhc.customcraft.craft.vocation.extractor.trinket.DionysusBrewTrinket;

public final class ExtractorVocationRegistry extends VocationRegistry {

  private static final ExtractorVocationRegistry REGISTRY = new ExtractorVocationRegistry();

  private final ExtractorPerk perk;

  private final ArtemisQuiverTrinket firstTrinket;
  private final DionysusBrewTrinket secondTrinket;

  private final ArrowEconomyCraft firstCraft;
  private final SugarEconomyCraft secondCraft;
  private final CompactBowCraft thirdCraft;
  private final SaddleCraft fourthCraft;

  private ExtractorVocationRegistry() {
    this.perk = register(new ExtractorPerk());
    this.firstTrinket = register(new ArtemisQuiverTrinket());
    this.secondTrinket = register(new DionysusBrewTrinket());
    this.firstCraft = register(new ArrowEconomyCraft());
    this.secondCraft = register(new SugarEconomyCraft());
    this.thirdCraft = register(new CompactBowCraft());
    this.fourthCraft = register(new SaddleCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "extractor";
  }

  @Override
  public ExtractorPerk getPerk() {
    return this.perk;
  }

  @Override
  public ArtemisQuiverTrinket getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public DionysusBrewTrinket getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public ArrowEconomyCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public SugarEconomyCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public CompactBowCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public SaddleCraft getFourthCraft() {
    return this.fourthCraft;
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }

  public static ExtractorVocationRegistry get() {
    return REGISTRY;
  }

}