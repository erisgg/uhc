package gg.eris.uhc.customcraft.craft.vocation.armorer;

import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.DemigodBoots;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.DemigodHelmet;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.ProtectionBook;
import gg.eris.uhc.customcraft.craft.vocation.armorer.perk.ArmorerPerk;
import gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets.DefendersMedallion;
import gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets.ZeusBlessing;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.DemigodBowCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.DemigodSwordCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.SharpnessBookCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.SoulThirsterCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.perk.DuelistPerk;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.ApollosBlessingTrinket;
import gg.eris.uhc.customcraft.craft.vocation.duelist.trinket.HydraToothTrinket;

public final class ArmorerVocationRegistry extends VocationRegistry {

  private static final ArmorerVocationRegistry REGISTRY = new ArmorerVocationRegistry();

  private final ArmorerPerk perk;
  private final DefendersMedallion firstTrinket;
  private final ZeusBlessing secondTrinket;
  private final DemigodHelmet firstCraft;
  private final DemigodBoots secondCraft;
  private final ProtectionBook thirdCraft;
  private final SoulThirsterCraft fourthCraft;

  private ArmorerVocationRegistry() {
    this.perk = register(new ArmorerPerk());
    this.firstTrinket = register(new DefendersMedallion());
    this.secondTrinket = register(new ZeusBlessing());
    this.firstCraft = register(new DemigodHelmet());
    this.secondCraft = register(new DemigodBoots());
    this.thirdCraft = register(new ProtectionBook());
    this.fourthCraft = register(new SoulThirsterCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "armorer";
  }

  @Override
  public ArmorerPerk getPerk() {
    return this.perk;
  }

  @Override
  public DefendersMedallion getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public ZeusBlessing getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public DemigodHelmet getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public DemigodBoots getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public ProtectionBook getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public SoulThirsterCraft getFourthCraft() {
    return this.fourthCraft;
  }

  public static ArmorerVocationRegistry get() {
    return REGISTRY;
  }

}
