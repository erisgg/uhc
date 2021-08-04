package gg.eris.uhc.customcraft.craft.vocation.duelist;

import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.DemigodBowCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.DemigodSwordCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.SharpnessBookCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.SoulThirsterCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.perk.DuelistPerk;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.ApollosBlessingTrinket;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.HydraToothTrinket;

public final class DuelistVocationRegistry extends VocationRegistry {

  private static final DuelistVocationRegistry REGISTRY = new DuelistVocationRegistry();

  private final DuelistPerk perk;
  private final HydraToothTrinket firstTrinket;
  private final ApollosBlessingTrinket secondTrinket;
  private final DemigodSwordCraft firstCraft;
  private final DemigodBowCraft secondCraft;
  private final SharpnessBookCraft thirdCraft;
  private final SoulThirsterCraft fourthCraft;

  private DuelistVocationRegistry() {
    this.perk = register(new DuelistPerk());
    this.firstTrinket = register(new HydraToothTrinket());
    this.secondTrinket = register(new ApollosBlessingTrinket());
    this.firstCraft = register(new DemigodSwordCraft());
    this.secondCraft = register(new DemigodBowCraft());
    this.thirdCraft = register(new SharpnessBookCraft());
    this.fourthCraft = register(new SoulThirsterCraft());
  }

  @Override
  public DuelistPerk getPerk() {
    return this.perk;
  }

  @Override
  public HydraToothTrinket getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public ApollosBlessingTrinket getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public DemigodSwordCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public DemigodBowCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public SharpnessBookCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public SoulThirsterCraft getFourthCraft() {
    return this.fourthCraft;
  }

  public static DuelistVocationRegistry get() {
    return REGISTRY;
  }

}
