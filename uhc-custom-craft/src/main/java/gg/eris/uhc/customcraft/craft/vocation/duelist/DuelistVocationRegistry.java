package gg.eris.uhc.customcraft.craft.vocation.duelist;

import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.duelist.perk.DuelistPerk;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.ApollosBlessingTrinket;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.HydraToothTrinket;

public final class DuelistVocationRegistry extends VocationRegistry {

  private static final DuelistVocationRegistry REGISTRY = new DuelistVocationRegistry();

  private final DuelistPerk perk;
  private final HydraToothTrinket firstTrinket;
  private final ApollosBlessingTrinket secondTrinket;

  private DuelistVocationRegistry() {
    this.perk = register(new DuelistPerk());
    this.firstTrinket = register(new HydraToothTrinket());
    this.secondTrinket = register(new ApollosBlessingTrinket());
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
  public Craft getFirstCraft() {
    return null;
  }

  @Override
  public Craft getSecondCraft() {
    return null;
  }

  @Override
  public Craft getThirdCraft() {
    return null;
  }

  @Override
  public Craft getFourthCraft() {
    return null;
  }

  public static DuelistVocationRegistry get() {
    return REGISTRY;
  }
}
